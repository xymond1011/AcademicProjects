#ifndef PRODUCT_H
#define PRODUCT_H
#include <string>
#include <algorithm>

class Product {
	protected:
		std::string product_id;
		std::string product_name;
		double product_price;
		
	public:
		//constructors
		Product();
		Product(std::string id, std::string name, double price);
		
		//setter
		void set_product_id(std::string id);
		void set_product_name(std::string name);
		void set_product_price(double price);
		virtual void set_quantity(int quantity) = 0;
			
		//getter
		std::string get_product_id() const;
		std::string get_product_name() const;
		double get_product_price() const;
		virtual int get_quantity() const = 0;
		
		//to_string(); virtual modifier for polymorphism
		virtual std::string to_string() const = 0;
};//end of Product ParentClass

class InventoryProduct : public Product {
	private:
		int product_quantity;
			
	public:
		//constructors
		InventoryProduct();
		InventoryProduct(std::string id, std::string name, double price, int quant);
		
		//setter
		void set_quantity(int quantity) override;
			
		//getter
		int get_quantity() const override;
			
		//to_string() method
		std::string to_string() const override; //overrides the parent class to_string() method
		
		//operator< method similar to compareTo method in Java.
		bool operator<(const InventoryProduct& other) const;
		
};//end of InventoryProduct SubClass

class POSProduct : public Product {
	private:
		std::string date_and_time;
		int order_quantity;
			
	public:
		//constructors
		POSProduct();
		POSProduct(std::string date_and_time, std::string id, std::string name, double price, int order);
		
		//setter
		void set_date_and_time(std::string date_time);
		void set_quantity(int order) override;
			
		//getter
		std::string get_date_and_time() const;
		int get_quantity() const override;
			
		//to_string() method
		std::string to_string() const override; //overrides the parent class to_string() method
		
		//operator< method similar to compareTo method in Java.
		bool operator<(const POSProduct& other) const;
		
};//end of POSProduct SubClass
#endif //end PRODUCT_H