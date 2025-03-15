#include <cstdlib>
#include <iostream>
#include <string>
#include "InventoryUtilities.cpp"
#include "POSUtilities.cpp"
#include "Product.cpp"

/*
Author: Alcazar, Xymond Louisse M.
Date: September 9, 2024
*/

class CanteenSystem {
	private:
		/*
			-> Displays the Menu for the Inventory System
		*/
		void inventoryMenu() {
			InventoryUtilities inv_util;
			std::vector<InventoryProduct> inv_product_list;
			const std::string INVENTORY_SOURCE_FILE = "res/product_inventory_list.csv";

			inv_util.populateProduct(inv_product_list, INVENTORY_SOURCE_FILE);

			while (true) {
				system("cls");
				char choice;

				std::cout<<"\n-- Inventory System --\n";
				std::cout<<"--------------------------\n\n";
				std::cout<<"What do you want to do?\n";
				std::cout<<"\n[1] Add Inventory\n[2] Check Inventory\n[3] Update Inventory\n[4] Delete Inventory\n[5] Search certain Product\n[6] Exit System\n";
				std::cout<<"--------------------------\n";
				std::cout<<"--> ";
				std::cin>>choice;
				std::cin.ignore();

				switch (choice) {
					case '1': {
						system("cls");
						inv_util.addProduct(inv_product_list);
						inv_util.appendContents(inv_product_list, INVENTORY_SOURCE_FILE);
						break;
					}
					case '2': {
						system("cls");
						std::cout<<"\n\n-- Product Inventory --\n\n";
						inv_util.showDetails(inv_product_list);
						std::cout<<"\n\n-- Press [ENTER] to go back to MENU --\n";
						std::getchar();
						break;
					}
					case '3': {
						system("cls");
						inv_util.updateProduct(inv_product_list);
						inv_util.overwriteContents(inv_product_list, INVENTORY_SOURCE_FILE);
						break;
					}
					case '4': {
						system("cls");
						std::cout<<"\n\n-- Current Products --\n\n";
						inv_util.showDetails(inv_product_list);//displays the current Product before deleting
						std::cout<<"\n\n-- Press [ENTER] to continue --\n\n";
						std::getchar();
						system("cls");

						inv_util.deleteContent(inv_product_list, inv_util.readProductID());
						inv_util.overwriteContents(inv_product_list, INVENTORY_SOURCE_FILE);
						break;
					}
					case '5': {
						system("cls");
						inv_util.searchProduct(inv_product_list, inv_util.readProductID());
						break;
					}
					case '6': {
						std::cout<<"-- Thank you for Using the Inventory System --\n\n";
						systemSwitch();
						break;
					}
					default:
						system("cls");
					std::cerr<<"-- Invalid ENTRY, press [ENTER] to try again --\n\n";
					std::getchar();
					break;
				}
			}
		}//end of inventoryMenu

		/*
			-> Displays the Menu for the POS System
		*/
		void posMenu() {
			POSUtilities pos_util;
			std::vector<POSProduct> pos_product_list;
			const std::string POS_SOURCE_FILE = "res/product_sales_list.csv";

			InventoryUtilities inv_util;
			std::vector<InventoryProduct> inv_product_list;
			const std::string INVENTORY_SOURCE_FILE = "res/product_inventory_list.csv";

			inv_util.populateProduct(inv_product_list, INVENTORY_SOURCE_FILE);

			while (true) {
				system("cls");
				char choice;

				std::cout<<"\n-- Point of Sale System --\n";
				std::cout<<"--------------------------\n\n";
				std::cout<<"What do you want to do?\n";
				std::cout<<"\n[1] Make a Sale\n[2] Total Product Sale\n[3] Search certain Product\n[4] Exit System\n";
				std::cout<<"--------------------------\n";
				std::cout<<"--> ";
				std::cin>>choice;
				std::cin.ignore();

				switch (choice) {
					case '1': {
						system("cls");
						pos_util.makeASale(inv_product_list);
						inv_util.overwriteContents(inv_product_list, INVENTORY_SOURCE_FILE);
						break;
					}
					case '2':
						system("cls");
						pos_util.populateProduct(pos_product_list, POS_SOURCE_FILE);
						pos_util.showTotalSales(pos_product_list, POS_SOURCE_FILE);
					break;
					case '3': {
						system("cls");
						inv_util.searchProduct(inv_product_list, inv_util.readProductID());
						break;
					}
					case '4':
						std::cout<<"-- Thank you for Using the POS System! --\n\n";
						systemSwitch();
					break;
					default:
						system("cls");
						std::cerr<<"-- Invalid ENTRY, press [ENTER] to try again --\n\n";
						std::getchar();
					break;
				}
			}
		}//end of posMenu

	public:
		void systemSwitch() {

			while (true) {
				system("cls");
				char mode;
				std::cout<<"-- Choose the System Mode --\n\n";
				std::cout<<"[P] POS Mode\n[I] Inventory Mode\n[Q] EXIT System\n\n";
				std::cout<<"--> ";
				std::cin>>mode;
				std::cin.ignore();
			
				switch (mode) {
					case 'P':
					case 'p':
						posMenu();
						break;
					case 'I':
					case 'i':
						inventoryMenu();
						break;
					case 'Q':
					case 'q':
						system("cls");
						std::cout<<"-- Thank you for Using the System --\n\n";
						exit(0);
					default:
						system("cls");
						std::cerr<<"-- Invalid ENTRY, press [ENTER] to try again --\n\n";
						std::getchar();
						break;
				}
			}
		}//end of systemSwitch
};//end of class

int main() {
	CanteenSystem main_menu;
	main_menu.systemSwitch();//main program
	return 0;
}