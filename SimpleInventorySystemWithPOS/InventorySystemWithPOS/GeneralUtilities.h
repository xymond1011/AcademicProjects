#ifndef GENERAL_UTILITIES_H
#define GENERAL_UTILITIES_H
#include <string>
#include <vector>

template <typename T> //to generalize the functions
class GeneralUtilities {
	public:
	
		//populates vector based on the file contents
		virtual void populateProduct(std::vector<T>& list, const std::string& filepath);
		
		//reads the ID of a Product Object
		virtual std::string readProductID();
		
		//reads the name of a Product Object
		virtual std::string readProductName();
		
		//reads the price of a Product Object
		virtual double readProductPrice();
		
		//reads the quantity of the Product Object
		virtual int readQuantity();
		
		//appends contents to an existing file
		virtual void appendContents(std::vector<T>& list, const std::string filepath);
		
		//overwrites existing file contents.
		virtual void overwriteContents(std::vector<T>& list, const std::string filepath);
		
};//end of GeneralUtilities class
#endif //end GENERAL_UTILITIES_H