#include "Product.h"
#include "GeneralUtilities.h"
#include <iostream>
#include <algorithm> //for the remove_if function
#include <fstream> //file stream
#include <sstream> //string stream
#include <iomanip> //table
#include <chrono> //for transaction time
#include <ctime> //for transaction time formatting
#include <vector>
#include <map>
#include <string>

class POSUtilities : public GeneralUtilities<POSProduct> {
	public:
		void populateProduct(std::vector<POSProduct>& list, const std::string& filepath) override {
			
			list.clear();//avoid duplicates
			
			//open the file
			//ifstream = input file stream, which means that this particular file is being used for reading purposes.
			std::ifstream file(filepath);
				
			//check if the file exists and is open successfully.
			if(!file.is_open()) {
				std::cerr<<"-- FILE not found --\n";
				return;
			}
			std::string line;
				
			//store the file contents (CSV) into the Product object.
			while(std::getline(file, line)) {
				std::stringstream ss(line);
				std::string date_and_time, id, name, price_str, quantity_str;
					
				//separate line contents using a comma (,) delimiter.
				std::getline(ss, date_and_time, ',');
				std::getline(ss, id, ',');
				std::getline(ss, name, ',');
				std::getline(ss, price_str, ',');
				std::getline(ss, quantity_str, ',');
				
				//to catch parsing errors
				try {
            		//convert the string versions of the price and quantity to their original types (i.e., double and int respectively).
					double price = std::stod(price_str);
					int quantity = std::stoi(quantity_str);
					
					//store the read file contents into the T object.
					POSProduct file_product(date_and_time, id, name, price, quantity);	
					list.push_back(file_product);
            	} catch (const std::invalid_argument& e) {
            		std::cerr<<"\n\n-- Invalid Argument --\n\n";
				} catch (const std::out_of_range& e) {
					std::cerr<<"\n\n-- Out of Range --\n\n";
				}
			}
		}//end of populateProduct
		
		/*
			-> Opens a file in append mode;
			-> It checks the provided file path and attempts to find the file;
			-> It sends an error message if the file is not found;
			-> It it appends the vector conetents into the file (i.e. *.csv).
		*/
		void appendContents(std::vector<POSProduct>& list, const std::string filepath) override {
			//open the file in append mode (std::ios::app).
			//ofstream = output file stream, meaning this file is being used for writing purposes.
			std::ofstream file(filepath, std::ios::app);
					
			//check if the file was opened successfully.
			if(!file.is_open()) {
				std::cerr<<"-- FILE could not be created --\n";
				return;
			}
					
			//sorts the objects based on the product name based on the operator< function.
			std::sort(list.begin(), list.end());
			
			//apppend the vector contents to the file.
			for(auto& products : list) {
				file<<products.to_string(); 
			}
		}//end of appendContents
		
		void overwriteContents(std::vector<POSProduct>& list, const std::string filepath) override {
			//open the file in output mode (std::ios::out).
			//ofstream = output file stream, meaning this file is being used for writing purposes.
			std::ofstream file(filepath, std::ios::out);
				
			//check if the file was opened successfully.
			if(!file.is_open()) {
				std::cerr<<"-- FILE not exist --\n";
				return;
			}
			
			//sorts the objects based on the product name based on the operator< function.
			std::sort(list.begin(), list.end());
			
			//overwrite the vector contents to the file.
			for(auto& products : list) {
				file<<products.to_string(); 
			}
		}//end of overwriteContents
		
		/*
 			-> Returns the product ID based on the user input.
		*/
		std::string readProductID() override {
			std::string id;
			std::cout<<"Enter Product ID: ";
			std::getline(std::cin, id);
			
			return id;
		}//end of readProductID
		
		/*
 			-> Returns the product name based on the user input.
		*/
		std::string readProductName() override {
			std::string name;
			std::cout<<"Enter Product Name: ";
			std::getline(std::cin, name);
			
			return name;
		}//end of readProductName
		
		/*
 			-> Returns the product price based on the user input.
		*/
		double readProductPrice() override {
			std::string price_str;
			std::cout<<"Enter Product Price: ";
			std::getline(std::cin, price_str);
			
			//to accept empty string. Mostly used in the updateInventory method
			if(price_str.empty()) {
				return -1;
			}
			return std::stod(price_str);
		}//end of readProductPrice
		
		/*
 			-> Returns the quantity of product based on the user input.
		*/
		int readQuantity() override {
			std::string quantity_str;
			std::cout<<"Enter Product Quantity: ";
			std::getline(std::cin, quantity_str);
			
			//to accept empty string. Mostly used in the updateInventory method
			if(quantity_str.empty()) {
				return -1;
			}
			return std::stoi(quantity_str);
		}//end of readProductQuantity
		
		/*
			-> Updates the file, mostly used when there is a correction.
			-> Get user input for Product Object.
			-> Overwrite the specific Object based on the ProductID.
			
		*/
		void updateProduct(std::vector<POSProduct>& list, double& update_quantity) {
			system("cls");
		    bool product_found = false;
		    std::string id_to_modify = readProductID();
		
		    for (auto& product : list) {
		        if (id_to_modify == product.get_product_id()) {
		            product_found = true;
		            system("cls");
		            std::cout<<"Current Order Quantity: "<<product.get_quantity();
		            std::cout<<"\n\n-- Press [ENTER] to keep the current field detail --\n\n";
		            
		            int new_quantity = readQuantity();
		            if (new_quantity >= 0) {
		                // Calculate the difference in quantity
		                int old_quantity = product.get_quantity();
		                product.set_quantity(new_quantity);
		                
		                // Update total_amount based on the difference
		                update_quantity += (new_quantity - old_quantity) * product.get_product_price();
		            }
		            break;
		        }
		    }
		
		    if (!product_found) {
		        std::cerr<<"\n\n-- Product with ID "<<id_to_modify<<" not found --\n";
		    } else {
		        std::cout<<"\n\n-- Product Updated successfully --\n";
		    }
		}//end of updateProduct method
		
		void makeASale(std::vector<InventoryProduct>& inv_list) {
			system("cls");
			
			//Get the current date and time
		    auto now = std::chrono::system_clock::now();
		    std::time_t now_time_t = std::chrono::system_clock::to_time_t(now);
		    std::tm now_tm = *std::localtime(&now_time_t);
		
		    //Format the date and time as "YYYY-MM-DD HH:MMAM/PM"
		    std::ostringstream date_and_time_stream;
		    date_and_time_stream<<std::put_time(&now_tm, "%Y-%m-%d %I:%M %p"); // %I for 12-hour clock, %p for AM/PM
		    std::string current_date_and_time = date_and_time_stream.str();
		    
		    std::string found_product_id, found_product_name;
		    std::vector<POSProduct> transaction_list;
    		double found_product_price = 0.0;
			double total_amount = 0.0;
			int total_quantity = 0;
			bool product_found = false;
			
			while (true) {
				system("cls");
				std::string product_id = readProductID();
				
				int quantity = readQuantity();
				
				//Find the product in the vector
				auto product_to_search = std::find_if(inv_list.begin(), inv_list.end(), [&product_id](const InventoryProduct& product) {
				    return product.get_product_id() == product_id; //returns true if product_id matched.
				});
				
				product_found = true;
				
				//gets the current_stock of the searched product
				int current_stock = product_to_search->get_quantity();
				    
				// Validate the product and stock inside the loop
			    if (product_to_search != inv_list.end()) {
			        //Store product details
			        found_product_id = product_to_search->get_product_id();
			        found_product_name = product_to_search->get_product_name();
			        found_product_price = product_to_search->get_product_price();
			        
			        int current_stock = product_to_search->get_quantity();
			        total_quantity = quantity;
			
			        if (current_stock <= 0 || quantity > current_stock) {
			            std::cout << "\n\n-- Not enough stock for product " << product_id << " --\n\n";
			            std::cout << "\n\n-- Press [ENTER] to go back to MENU --\n\n";
			            std::getchar();
			            return;
			        }
					//updates the current stock by subtracting the order quantity to the current stock.
					int updated_stock = current_stock - quantity;
					product_to_search->set_quantity(updated_stock);
					double total_per_product = product_to_search->get_product_price() * quantity;
					total_amount += total_per_product;
					
					//creates a POSProduct object using the Object involved in the transaciton.
					POSProduct sold_product(current_date_and_time,
											found_product_id, 
											found_product_name, 
											found_product_price,
											total_quantity);
										
					//store transaction to a vector
					transaction_list.push_back(sold_product);
				}
				std::string another_sale;
				std::cout<<"\n\n-- Press [A] to add more product, [ENTER] to continue --\n\n";
				std::getline(std::cin, another_sale);
								
				if(another_sale != "A" && another_sale != "a") {
					break;
				}
			}
		
			while (true) {
				if(product_found) {
					system("cls");
					std::cout<<"-- Sale Details --\n\n";
					
					//print table header
					std::cout<<std::left
							 <<std::setw(25)<<"Date & Time"
							 <<std::setw(15)<<"Product ID"
						     <<std::setw(25)<<"Product Name"
						   	 <<std::setw(10)<<"Price"
					         <<std::setw(10)<<"Order Quantity"
							 <<std::endl;
										 
					//print product details in the table
					for(auto& product_sold : transaction_list) {
						std::cout<<std::left
							 <<std::setw(25)<<product_sold.get_date_and_time()
							 <<std::setw(15)<<product_sold.get_product_id()
					       	 <<std::setw(25)<<product_sold.get_product_name()
					       	 <<std::setw(10)<<product_sold.get_product_price()
					       	 <<std::setw(10)<<product_sold.get_quantity()
							 <<std::endl;
					}
					//print a separator line
					std::cout << std::string(95, '-')<<std::endl; 
								
					//display the amount to be paid
					std::cout<<std::left
							 <<std::setw(5)<<"TOTAL: "<<std::setw(10)<<total_amount<<std::endl;
					
					std::string del_or_updt;
					std::cout<<"\n\n-- Press [X] to remove a product, [U] to correct the entry, [ENTER] to proceed --\n\n";
					std::getline(std::cin, del_or_updt);
					
					if(del_or_updt  == "X" || del_or_updt == "x") {
						system("cls");
			            std::string product_to_remove = readProductID();
			            auto remove_product = std::find_if(transaction_list.begin(), transaction_list.end(), 
			                                    [&product_to_remove](const POSProduct& product) {
			                                        return product.get_product_id() == product_to_remove;
			                                    });
			                                    
			            if (remove_product != transaction_list.end()) {
			                //Update the total_amount by subtracting the price of the product to be removed
			                total_amount -= (remove_product->get_product_price() * remove_product->get_quantity());
			                //Remove the product from the transaction list
			                transaction_list.erase(remove_product);
			        	}
					} else if(del_or_updt == "U" || del_or_updt == "u") {
						system("cls");
					 	updateProduct(transaction_list, total_amount);
					} else {
						double paid_amount = 0.0;
						std::cout<<"\n\n-- Payment --\n\n";
						std::cout<<"Amount Paid: ";
						std::cin>>paid_amount;
						std::cin.ignore();
						double change = std::abs(total_amount - paid_amount);
						
						std::cout<<std::left
							 <<std::setw(5)<<"CHANGE: "<<std::setw(10)<<change<<std::endl;
						std::cout<<"\n-- Transanction Success, Press [ENTER] to go back to MENU --\n\n";
						std::getchar();
						break;
					}
					
					std::cout<<"\n\n-- Press [ENTER] to continue --\n\n";
			        std::getchar();	
						
					appendContents(transaction_list, "res/product_sales_list.csv");	
				} else {
					std::cout<<"\n\n-- Product "<<found_product_id<<" not found --";
					        
				    std::cout<<"\n\n-- Press [ENTER] to go back to MENU --\n\n";
			        std::getchar();
				}
			}
		}//end of makeASale
		
		void showTotalSales(std::vector<POSProduct>& list, const std::string filepath) {
			
			std::map<std::string, POSProduct> product_map;

		    //Adding quantities of duplicate products
		    for (auto& product : list) {
		        const std::string& id = product.get_product_id();
		        
		        if (product_map.find(id) != product_map.end()) {
		        	//adds quantities of similar products
		            int new_quantity = product_map[id].get_quantity() + product.get_quantity();
            		product_map[id].set_quantity(new_quantity);
		        } else {
		            // Product does not exist, add it to the map
		            product_map[id] = POSProduct(
		                product.get_date_and_time(),id,
		                product.get_product_name(),
		                product.get_product_price(),
		                product.get_quantity()
		            );
		        }
		    }
		    system("cls");
		    std::cout<<"\n\n-- Product Sales --\n\n";
		    //print table header
			std::cout<<std::left
					 <<std::setw(25)<<"Date & Time"
					 <<std::setw(15)<<"Product ID"
			         <<std::setw(30)<<"Product Name"
			         <<std::setw(10)<<"Price"
			         <<std::setw(10)<<"Order Quantity"
					 <<std::endl;
			    
			//print a separator line
			std::cout << std::string(95, '-') << std::endl; 
			
			//loop through each Product in the vector and display its attributes
			for (const auto& product : list) {
				
			    std::cout<<std::left
			    		 <<std::setw(25)<<product.get_date_and_time()
						 <<std::setw(15)<<product.get_product_id()
			           	 <<std::setw(30)<<product.get_product_name()
			           	 <<std::setw(10)<<product.get_product_price()
			           	 <<std::setw(10)<<product.get_quantity()
						 <<std::endl;
			}
			std::cout<<"\n\n-- Press [ENTER] to go back to continue --\n\n";
			std::getchar();
			
			system("cls");
			std::cout<<"\n\n-- Total Sales per Product --\n\n";
			//print table header
			std::cout<<std::left
					 <<std::setw(15)<<"Product ID"
			         <<std::setw(30)<<"Product Name"
			         <<std::setw(10)<<"Price"
			         <<std::setw(25)<<"Order Quantity"
			         <<std::setw(25)<<"Total Sales"
					 <<std::endl;
			    
			//print a separator line
			std::cout << std::string(91, '-') << std::endl; 
			
			//loop through each Product in the vector and display its attributes
			for (const auto& entry : product_map) {
				
				const POSProduct& product = entry.second;
				
			    std::cout<<std::left
						 <<std::setw(15)<<product.get_product_id()
			           	 <<std::setw(30)<<product.get_product_name()
			           	 <<std::setw(10)<<product.get_product_price()
			           	 <<std::setw(25)<<product.get_quantity()
			           	 <<std::setw(25)<<product.get_product_price() * product.get_quantity()
						 <<std::endl;
			}
			
			std::cout<<"\n\n-- Press [ENTER] to go back to MENU --\n\n";
			std::getchar();
		}//end of showTotalSales
};