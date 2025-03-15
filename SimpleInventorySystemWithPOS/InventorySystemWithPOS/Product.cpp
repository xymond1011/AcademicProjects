#include "Product.h"

//constructor implementation for Product Parent Class; used initializer list for efficiency
Product::Product() : product_id(""), product_name(""), product_price(0.0) {}

Product::Product(std::string id, std::string name, double price) : product_id(id), product_name(name), product_price(price) {}

//setter implementation for the Product Parent Class
void Product::set_product_id(std::string id) {
	product_id = id;
}
void Product::set_product_name(std::string name) {
	product_name = name;
}
void Product::set_product_price(double price) {
	product_price = price;
}

//getter implementation for the Product Parent Class
std::string Product::get_product_id() const {
	return product_id;
}
std::string Product::get_product_name() const {
	return product_name;
}
double Product::get_product_price() const {
	return product_price;
}

//to_string() implementation for the Product Parent Class
std::string Product::to_string() const {
	return product_id+","+product_name+","+std::to_string(product_price);
}
//end of the Implementation for the Product Parent Class

// ============================================================================ //

//constructor implementation for InventoryProduct; used initializer list for efficiency
InventoryProduct::InventoryProduct() : Product(), product_quantity(0) {}

InventoryProduct::InventoryProduct(std::string id, std::string name, double price, int quant) 
	: Product(id, name, price), product_quantity(quant) {}

//setter implementation for InventoryProduct Sub Class
void InventoryProduct::set_quantity(int quant) {
	product_quantity = quant;
}

//getter implementation for InventoryProduct Sub Class
int InventoryProduct::get_quantity() const {
	return product_quantity;
}

//to_string() implementation for the InventoryProduct Sub Class
std::string InventoryProduct::to_string() const {
	return Product::to_string()+","+std::to_string(product_quantity)+"\n";
}

/*
	-> Compares the product based on the Product Name.
	-> operator used for the sort function. 
	-> Similar to compareTo method in Comparable interface in Java.
	-> required to implement to use the std::sort function.
*/
bool InventoryProduct::operator<(const InventoryProduct& other) const {
			
        return this->get_product_name() < other.get_product_name();
        	
}//end of operator<

//end of the Implementation for the InventoryProduct Sub Class

// ============================================================================ //

//constructor implementation for POSProduct; used initializer list for efficiency
POSProduct::POSProduct() : Product(), date_and_time(""), order_quantity(0) {}

POSProduct::POSProduct(std::string date_and_time, std::string id, std::string name, double price, int order) 
	: date_and_time(date_and_time), Product(id, name, price), order_quantity(order) {}

//setter implementation for POSProduct Sub Class
void POSProduct::set_date_and_time(std::string date_and_time) {
	this->date_and_time = date_and_time;
}

void POSProduct::set_quantity(int order) {
	order_quantity = order;
}

//getter implementation for POSProduct Sub Class
std::string POSProduct::get_date_and_time() const {
	return date_and_time;
}

int POSProduct::get_quantity() const {
	return order_quantity;
}

//to_string() implementation for the POSProduct Sub Class
std::string POSProduct::to_string() const {
	return date_and_time+","+Product::to_string()+","+std::to_string(order_quantity)+"\n";
}

/*
	-> Compares the product based on the Product Name.
	-> operator used for the sort function. 
	-> Similar to compareTo method in Comparable interface in Java.
	-> required to implement to use the std::sort function.
*/
bool POSProduct::operator<(const POSProduct& other) const {
			
    return this->get_date_and_time() < other.get_date_and_time();
        	
}//end of operator<

//end of the Implementation for the POSProduct Sub Class
