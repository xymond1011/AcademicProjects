#include "Product.h"
#include "GeneralUtilities.h"
#include <iostream>
#include <algorithm> //for remove_if, find_if and sort function.
#include <fstream> //file stream
#include <sstream> //string stream
#include <iomanip> //table
#include <vector>
#include <string>

class InventoryUtilities : public GeneralUtilities<InventoryProduct> {
	public:
		/*
			-> Opens a file in read mode;
			-> It checks the provided file path and attempts to find the file;
			-> It sends an error message if the file is not found;
			-> It populates the vector with the file contents.
		*/
		void populateProduct(std::vector<InventoryProduct>& list, const std::string& filepath) override {
			
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
				std::string id, name, price_str, quantity_str;
					
				//separate line contents using a comma (,) delimiter.
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
					InventoryProduct file_product(id, name, price, quantity);	
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
		void appendContents(std::vector<InventoryProduct>& list, const std::string filepath) override {
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
			
			for(auto& products : list) {
				file<<products.to_string(); 
			}
		}//end of appendContents
		
		void overwriteContents(std::vector<InventoryProduct>& list, const std::string filepath) override {
			//open the file in output mode (std::ios::out) with truncation (std::ios::trunc).
			//ofstream = output file stream, meaning this file is being used for writing purposes.
			std::ofstream file(filepath, std::ios::out | std::ios::trunc);
				
			//check if the file was opened successfully.
			if(!file.is_open()) {
				std::cerr<<"-- FILE not exist --\n";
				return;
			}

			//sorts the objects based on the product name based on the operator< function.
			std::sort(list.begin(), list.end());			
			
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
			std::cout<<"Enter Stock (Quantity): ";
			std::getline(std::cin, quantity_str);
			
			//to accept empty string. Mostly used in the updateInventory method
			if(quantity_str.empty()) {
				return -1;
			}
			return std::stoi(quantity_str);
		}//end of readProductQuantity
		
		/*
 			-> Returns product details for the inventory system based on user input.
 			
 			-> quantity_name is for the quantity title customization.
 				-> Inventory and POS has different implementation of quantity.
		*/
		InventoryProduct readProductDetails() {
			system("cls");
			InventoryProduct prod;

			//set Product object attributes
			prod.set_product_id(readProductID());
			prod.set_product_name(readProductName());
			prod.set_product_price(readProductPrice());
    		prod.set_quantity(readQuantity());
		
			return prod;
		}//end of readProductDetails
		
		
		/*
			-> Displays the product details.
			-> used for both Inventory and POS Systems
			
			-> quantity_header parameter is for customization of the InventoryProductable's quantity column.
		*/
		void showDetails(std::vector<InventoryProduct>& list) const {
				
			//print table header
			std::cout<<std::left
					 <<std::setw(15)<<"Product ID"
			         <<std::setw(25)<<"Product Name"
			         <<std::setw(10)<<"Price"
			         <<std::setw(10)<<"Stock (Quantity)"
					 <<std::endl;
			    
			//print a separator line
			std::cout << std::string(70, '-') << std::endl; 
			
			//loop through each Product in the vector and display its attributes
			for (const auto& product : list) {
			    std::cout<<std::left
						 <<std::setw(15)<<product.get_product_id()
			           	 <<std::setw(25)<<product.get_product_name()
			           	 <<std::setw(10)<<product.get_product_price()
			           	 <<std::setw(10)<<product.get_quantity()
						 <<std::endl;
			}
		}//end of showDetails
		
		/*
			-> Writes user input for Product Object to the file (i.e. *.csv).
			
		*/
		void addProduct(std::vector<InventoryProduct>& list) {
			InventoryProduct new_product;//initialize Product Object for new entries.
		
			while (true) {
			 	new_product = readProductDetails();
			 
			 	//add product object to productList vector.
				list.push_back(new_product);
			
        		system("cls");
        		std::cout<<"-- Do you want to add more? <y/n>: ";
        		char choice;
        		std::cin>>choice;
        		std::cin.ignore();
        	
        		switch (choice) {
        			case 'y':
		       		case 'Y':
		       			continue;
		       			break;
		       		case 'n':
		       		case 'N':
		       			break;
		        		default:
		       			std::cerr<<"-- Invalid ENTRY, press [ENTER] to try again --\n\n";
        				std::getchar();
        				continue;
				}
				break; //breaks the loop when entered <n/N>.
			}
		}//end of addProduct method
		
		/*
			-> Updates the file, mostly used when there is a correction.
			-> Get user input for Product Object.
			-> Overwrite the specific Object based on the ProductID.
			
		*/
		void updateProduct(std::vector<InventoryProduct>& list) {
			system("cls");
			bool product_found = false;
			
			std::cout<<"-- Current Products --\n\n";
			showDetails(list);
			std::cout<<"\n\n-- Press [ENTER] to continue --\n\n";
			std::getchar();
		
			system("cls");
			std::string id_to_modify = readProductID();
			
			for(auto& product : list) {
					
				if(id_to_modify == product.get_product_id()) {
					product_found = true;
					system("cls");
						
					std::cout<<"-- Modify Product --\n\n";
					std::cout<<"Current ID: "<<product.get_product_id()<<"\n";
		           	std::cout<<"\n-- Press [ENTER] to keep the current field detail --\n\n";
		           	std::string new_ID = readProductID();
		           	if (!new_ID.empty()) product.set_product_id(new_ID);
		            	
		           	system("cls");
					std::cout<<"Current Name: "<<product.get_product_name()<<"\n";
		           	std::cout<<"\n-- Press [ENTER] to keep the current field detail --\n\n";
		           	std::string new_name = readProductName();
		           	if (!new_name.empty()) product.set_product_name(new_name);
		
					system("cls");
		           	std::cout<<"Current Price: "<<product.get_product_price()<<"\n";
		           	std::cout<<"\n-- Press [ENTER] to keep the current field detail --\n\n";
		           	double new_price = readProductPrice();
		           	if (new_price >= 0) product.set_product_price(new_price);
						
					system("cls");
		           	std::cout<<"Current Stock: "<<product.get_quantity()<<"\n";
		           	std::cout<<"\n-- Press [ENTER] to keep the current field detail --\n\n";
		           	int new_quantity = readQuantity();
		           	if (new_quantity >=0) product.set_quantity(new_quantity);
		
		           	break;
				}	
			}
				
			if (!product_found) {
		       	std::cerr<<"\n\n-- Product with ID "<<id_to_modify<<" not found --\n";
		       	std::cout<<"\n\n-- Press [ENTER] to continue --\n\n";
		       	std::getchar();
		   	} else {
		       	std::cout<<"\n\n-- Product Updated successfully --\n\n\n";
		       	std::cout<<"\n-- Press [ENTER] to go back to MENU --\n\n";
		       	std::getchar();
		   	}
		}//end of updateProduct method
		
		/*
			-> Deletes a Product Object based on the Product ID;
			-> Used remove_if function that has 3 parameters (i.e. beginning range, ending range
					and a predicate)
				-> The third parameter is a lambda function.
				-> [&product_id] a variable that lambda access directly;
				-> (const InventoryProduct& product) a product Object variable;
		*/
		void deleteContent(std::vector<InventoryProduct>& list, const std::string& product_id) {
			
		   	system("cls");
		    //Find the product in the vector
		    auto product_to_delete = std::remove_if(list.begin(), list.end(), [&product_id](const InventoryProduct& product) {
		        return product.get_product_id() == product_id;//returns true if product_id matched.
		    });
		
		    //Check if any products were found and removed
		    if (product_to_delete != list.end()) {
		    	
		    	//erase the product Object that the remove_if function returns
		        list.erase(product_to_delete, list.end()); 
		        
		        std::cout<<"\n\n-- Product "<<product_id<<" has been deleted --";
		        
		        std::cout<<"\n\n-- Press [ENTER] to go back to MENU --\n\n";
		        std::getchar();
		        
		    } else {
		    	
		        std::cout<<"\n\n-- Product "<<product_id<<" not found --";
		        
		        std::cout<<"\n\n-- Press [ENTER] to go back to MENU --\n\n";
		        std::getchar();
		        
		    }
		} // end of deleteContent
		
		void searchProduct(std::vector<InventoryProduct>& list, const std::string& product_id) {
			system("cls");
		    //Find the product in the vector
		    auto product_to_search = std::find_if(list.begin(), list.end(), [&product_id](const InventoryProduct& product) {
		    	return product.get_product_id() == product_id; //returns true if product_id matched.
		    });
		    
		    if(product_to_search != list.end()) {
		    	std::cout<<"-- Product "<<product_id<<" Details --\n\n";
		    	
		    	//print table header
				std::cout<<std::left
					 	 <<std::setw(15)<<"Product ID"
			        	 <<std::setw(25)<<"Product Name"
			       		 <<std::setw(10)<<"Price"
			        	 <<std::setw(10)<<"Stock (Quantity)"
						 <<std::endl;
						 
				//print a separator line
				std::cout << std::string(70, '-') << std::endl; 
				
				//print product details in the table
					//used arrows (->) instead of dot (.) because product_to_search is an iterator not an Object.
				std::cout<<std::left
						 <<std::setw(15)<<product_to_search->get_product_id()
			           	 <<std::setw(25)<<product_to_search->get_product_name()
			           	 <<std::setw(10)<<product_to_search->get_product_price()
			           	 <<std::setw(10)<<product_to_search->get_quantity()
						 <<std::endl;
				
		    	std::cout<<"\n\n-- Press [ENTER] to go back to MENU --\n";
				std::getchar();
				
			} else {
				
				std::cout<<"\n\n-- Product "<<product_id<<" not found --";
		        
		        std::cout<<"\n\n-- Press [ENTER] to go back to MENU --\n\n";
		        std::getchar();
			}
		}//end of searchProduct
};//end of InventoryUtilities class
