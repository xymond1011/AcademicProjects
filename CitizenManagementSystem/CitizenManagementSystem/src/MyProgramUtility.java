import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

/**
 Made By: Xymond Louisse M. Alcazar
 */
public class MyProgramUtility {
    //instantiation of arrayList and Citizen class object, so it can be used in different methods
    ArrayList<Citizen> citizenList = new ArrayList<>();
    Citizen citizen;

    /**
     Returns the file it has read and separates the neccesary data using the split() method.

     Made By: Xymond Louisse M. Alcazar
     */
    public void fileReader(String filePath) throws FileNotFoundException {
        Scanner reader = new Scanner(new File(filePath));

        while(reader.hasNextLine()) {
            String readLines = reader.nextLine();
            String[] content = readLines.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            citizen = new Citizen(content[0]+" "+content[1],content[2], content[3].replace("\"", ""), Integer.parseInt(content[4]), content[5].equalsIgnoreCase("resident"), Integer.parseInt(content [6]), content[7].charAt(0));
            citizenList.add(citizen);
        }
        reader.close();
    }

    //Table Displayed as the Application runs
    /**
     Returns a table filled with the data from the fileReader

     Made By: Xymond Louisse M. Alcazar
     */
    public JTable populateTable() {

        Object[] columnNames = {"Name","Email","Address","Age","Residency","District","Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for(Citizen cit : citizenList) {
            Object[] row = {cit.getFullName(),cit.getEmail(),cit.getAddress(),cit.getAge(),cit.getResidency(),cit.getDistrict(),cit.getGender()};
            model.addRow(row);
        }

        JTable table = new JTable(model);
        //Resizes the column widths (By: Xymond Louisse M. Alcazar
        table.getColumnModel().getColumn(0).setPreferredWidth(200);
        table.getColumnModel().getColumn(1).setPreferredWidth(350);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(50);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);

        //To make cells center alignment (By: Xymond Louisse M. Alcazar)
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(3).setCellRenderer(centerAlignment);
        table.getColumnModel().getColumn(5).setCellRenderer(centerAlignment);
        table.getColumnModel().getColumn(6).setCellRenderer(centerAlignment);

        table.setEnabled(false);

        return table;
    }

//Residency Tables
    //method for building filtered Citizen Table via Residency (Made By: Xymond Louisse M. Alcazar)
    /**
     Returns a table filled with the specific data from fileReader (i.e. Residency)

     Made By: Xymond Louisse M. Alcazar
     */
    public JTable residencyTable(String residency) {

        JTable residentTable = buildResidencyTable(residency);
        //Resizes the column widths (By: Xymond Louisse M. Alcazar
        residentTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        residentTable.getColumnModel().getColumn(1).setPreferredWidth(350);
        residentTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        residentTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        residentTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        residentTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        residentTable.getColumnModel().getColumn(6).setPreferredWidth(50);

        //To make cells center alignment (By: Xymond Louisse M. Alcazar)
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);

        residentTable.getColumnModel().getColumn(3).setCellRenderer(centerAlignment);
        residentTable.getColumnModel().getColumn(4).setCellRenderer(centerAlignment);
        residentTable.getColumnModel().getColumn(5).setCellRenderer(centerAlignment);
        residentTable.getColumnModel().getColumn(6).setCellRenderer(centerAlignment);

        residentTable.setEnabled(false);

        return residentTable;
    }
    /**
     Fills the table with the data that was specified in the residencyTable

     Made By: Xymond Louisse M. Alcazar
     */
    private JTable buildResidencyTable(String residency) {
        ArrayList<Citizen> arrayList = new ArrayList<>(citizenList);
        arrayList.sort(Citizen::compareTo);//sorts based on names - Alcazar
        Object[] columnNames = {"Name","Email","Address","Age","Residency","District","Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Citizen citizens : arrayList) {
            if (citizens.getResidency().equalsIgnoreCase(residency)) {
                Object[] row = {citizens.getFullName(),citizens.getEmail(),citizens.getAddress(),citizens.getAge(),citizens.getResidency(),citizens.getDistrict(),citizens.getGender()};
                model.addRow(row);
            }
        }
        return new JTable(model);
    }
    //Total
    /**
     Returns the total number of citizens based on the specified data (i.e. Residency)

     Made By: Xymond Louisse M. Alcazar
     */
    public int getTotalResidency(String residency) {
        ArrayList<Citizen> arrayList = new ArrayList<>(citizenList);
        int value = 0, total;

        for (Citizen citizens : arrayList) {
            if(citizens.getResidency().equalsIgnoreCase(residency)) {
                value++;
            }
        }
       total = value;

        return total;
    }

//Age Range Tables

    /**
     Return a table filled with the specific data from fileReader (i.e. Age)

     Made By: Xymond Louisse M. Alcazar
     */
    public JTable youngAdultTable() {
        JTable youngAdultsTable = buildAgeTable(18, 30);
        //Resizes the column widths (By: Xymond Louisse M. Alcazar
        youngAdultsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        youngAdultsTable.getColumnModel().getColumn(1).setPreferredWidth(350);
        youngAdultsTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        youngAdultsTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        youngAdultsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        youngAdultsTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        youngAdultsTable.getColumnModel().getColumn(6).setPreferredWidth(50);

        //To make cells center alignment (By: Xymond Louisse M. Alcazar)
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);

        youngAdultsTable.getColumnModel().getColumn(3).setCellRenderer(centerAlignment);
        youngAdultsTable.getColumnModel().getColumn(5).setCellRenderer(centerAlignment);
        youngAdultsTable.getColumnModel().getColumn(6).setCellRenderer(centerAlignment);

        youngAdultsTable.setEnabled(false);

        return youngAdultsTable;
    }
    /**
     Return a table filled with the specific data from fileReader (i.e. Age)

     Made By: Xymond Louisse M. Alcazar
     */
    public JTable middleAgeTable() {
        JTable middleAgesTable = buildAgeTable(31, 45);
        //Resizes the column widths (By: Xymond Louisse M. Alcazar
        middleAgesTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        middleAgesTable.getColumnModel().getColumn(1).setPreferredWidth(350);
        middleAgesTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        middleAgesTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        middleAgesTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        middleAgesTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        middleAgesTable.getColumnModel().getColumn(6).setPreferredWidth(50);

        //To make cells center alignment (By: Xymond Louisse M. Alcazar)
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);

        middleAgesTable.getColumnModel().getColumn(3).setCellRenderer(centerAlignment);
        middleAgesTable.getColumnModel().getColumn(5).setCellRenderer(centerAlignment);
        middleAgesTable.getColumnModel().getColumn(6).setCellRenderer(centerAlignment);

        middleAgesTable.setEnabled(false);

        return middleAgesTable;
    }
    /**
     Return a table filled with the specific data from fileReader (i.e. Age)

     Made By: Xymond Louisse M. Alcazar
     */
    public JTable oldAdultTable() {
        JTable oldAdultsTable = buildAgeTable(46, 70);
        //Resizes the column widths (By: Xymond Louisse M. Alcazar
        oldAdultsTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        oldAdultsTable.getColumnModel().getColumn(1).setPreferredWidth(350);
        oldAdultsTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        oldAdultsTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        oldAdultsTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        oldAdultsTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        oldAdultsTable.getColumnModel().getColumn(6).setPreferredWidth(50);

        //To make cells center alignment (By: Xymond Louisse M. Alcazar)
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);

        oldAdultsTable.getColumnModel().getColumn(3).setCellRenderer(centerAlignment);
        oldAdultsTable.getColumnModel().getColumn(5).setCellRenderer(centerAlignment);
        oldAdultsTable.getColumnModel().getColumn(6).setCellRenderer(centerAlignment);

        oldAdultsTable.setEnabled(false);

        return oldAdultsTable;
    }
    /**
     Fills the table with the data that was specified in the youngAdultTable, middleAgeTable and oldAdultTable

     Made By: Xymond Louisse M. Alcazar
     */
    private JTable buildAgeTable(int min, int max) {
        ArrayList<Citizen> arrayList = new ArrayList<>(citizenList);
        arrayList.sort(Citizen::compareTo);//sorts based on names - Alcazar

        Citizen[] citizenArray = arrayList.toArray(new Citizen[0]);

        Object[] columnNames = {"Name","Email","Address","Age","Residency","District","Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (int i = 0; i < citizenArray.length - 1; i++) {
            for (int j = 0; j < citizenArray.length - i - 1; j++) {
                if (citizenArray[j].getAge() > citizenArray[j + 1].getAge()) {
                    Citizen temp = citizenArray[j];
                    citizenArray[j] = citizenArray[j + 1];
                    citizenArray[j + 1] = temp;
                }
            }
        }

        for (Citizen citizens : citizenArray) {
            if (citizens.getAge() >= min && citizens.getAge() <= max) {
                Object[] row = {citizens.getFullName(),citizens.getEmail(),citizens.getAddress(),citizens.getAge(),citizens.getResidency(),citizens.getDistrict(),citizens.getGender()};
                model.addRow(row);
            }
        }
        return new JTable(model);
    }
    /**
     Returns the total number of citizens based on the specified data (i.e. Age)

     Made By: Xymond Louisse M. Alcazar
     */
    public int getTotalAgeRange(String range) {
        ArrayList<Citizen> arrayList = new ArrayList<>(citizenList);
        int value = 0, total;

        if (range.equalsIgnoreCase("youngAdults")) {
            for (Citizen citizens : arrayList) {
                if(citizens.getAge() >= 18 && citizens.getAge() <= 30) {
                    value++;
                }
            }
        } else if(range.equalsIgnoreCase("middleAges")) {
            for (Citizen citizens : arrayList) {
                if(citizens.getAge() >= 31 && citizens.getAge() <= 45) {
                    value++;
                }
            }
        } else if(range.equalsIgnoreCase("oldAdults")) {
            for (Citizen citizens : arrayList) {
                if(citizens.getAge() >= 46 && citizens.getAge()<= 70) {
                    value++;
                }
            }
        }

        total = value;

        return total;
    }

    /**
     Returns the table of district filled with the necessary data

     Made By: John Andrew Angcaway
     */
    public JTable districtTable() {

        JTable districtTable = buildDistrictSortTable();

        //Resizes the column widths (By: Xymond Louisse M. Alcazar
        districtTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        districtTable.getColumnModel().getColumn(1).setPreferredWidth(350);
        districtTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        districtTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        districtTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        districtTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        districtTable.getColumnModel().getColumn(6).setPreferredWidth(50);

        //To make cells center alignment (By: Xymond Louisse M. Alcazar)
        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(JLabel.CENTER);

        districtTable.getColumnModel().getColumn(3).setCellRenderer(centerAlignment);
        districtTable.getColumnModel().getColumn(5).setCellRenderer(centerAlignment);
        districtTable.getColumnModel().getColumn(6).setCellRenderer(centerAlignment);

        districtTable.setEnabled(false);

        return districtTable;
    }
    /**
     Fills a table with the necessary data

     Made By: Xymond Louisse M. Alcazar
     */
    //District Table Builder
    public JTable buildDistrictSortTable() {
        citizenList.sort(Citizen::compareTo);//sorts based on names - Alcazar
        Citizen[] citizenArray = citizenList.toArray(new Citizen[0]);

        Object[] columnNames = {"Name","Email","Address","Age","Residency","District","Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        //Sorter Made By: John Andrew Angcaway
        for (int i = 0; i < citizenArray.length - 1; i++) {
            for (int j = 0; j < citizenArray.length - i - 1; j++) {
                if (citizenArray[j].getDistrict() > citizenArray[j + 1].getDistrict()) {
                    Citizen temp = citizenArray[j];
                    citizenArray[j] = citizenArray[j + 1];
                    citizenArray[j + 1] = temp;
                }
            }
        }

        int currentDistrict = 0, districtNumber = 1;

        //fills up the table with the sorted data
        for(Citizen citizen: citizenArray) {

            //to set blank between the different districts also putting a title at the beginning of a new district number.
            if(currentDistrict != citizen.getDistrict()) {

                // '\b\ adds some square symbol in the table. I used that to make a design every district number.
                Object[] blankRow = {"\b","","","","","",""};
                Object[] blankRowWithTitle = {"\bDISTRICT "+districtNumber,"","","","","",""};
                model.addRow(blankRow);
                model.addRow(blankRowWithTitle);
                model.addRow(blankRow);
                currentDistrict = citizen.getDistrict();
                districtNumber++;
            }
            Object[] rowData = {citizen.getFullName(), citizen.getEmail(), citizen.getAddress(), citizen.getAge(), citizen.getResidency(), citizen.getDistrict(), citizen.getGender()};
            model.addRow(rowData);
        }

        return new JTable(model);
    }
    /**
     Returns an integer based on the counted specific type of addresses

     Made By: Xymond Louisse M. Alcazar
     */
    public int countCitizenApartmentAddress() {
        int apartmentCount = 0;
        for(Citizen citizen : citizenList) {
            if(citizen.getAddress().charAt(0) == 'A') {
                apartmentCount++;
            }
        }
        return apartmentCount;
    }
    /**
     Returns an integer based on the counted specific type of addresses

     Made By: Xymond Louisse M. Alcazar
     */
    public int countCitizenHomeAddress() {
        int homeCount = 0;
        for(Citizen citizen : citizenList) {
            if(citizen.getAddress().charAt(0) == 'P') {
                homeCount++;
            }
        }
        return homeCount;
    }
    /**
     Returns an integer based on the counted specific type of addresses

     Made By: Xymond Louisse M. Alcazar
     */
    public int countCitizenPostOfficeAddress() {
        int postOfficeCount = 0;
        for(Citizen citizen : citizenList) {
            if(citizen.getAddress().charAt(0) != 'A' && citizen.getAddress().charAt(0) != 'P') {
                postOfficeCount++;
            }
        }
        return postOfficeCount;
    }


    /**
     Saves the data into a txt file

     Made By: Michael Justine R. Baladad || Modified By: Xymond Louisse M. Alcazar
     */
    //residency -> Made By: Michael Justine R. Baladad
    public List<Citizen> getResidents() {
        citizenList.sort(Citizen::compareTo);//sorts based on names - Alcazar
        return citizenList.stream()
                .filter(citizen -> "Resident".equals(citizen.getResidency()))
                .collect(Collectors.toList());
    }
    public List<Citizen> getNonResidents() {
        citizenList.sort(Citizen::compareTo);//sorts based on names - Alcazar
        return citizenList.stream()
                .filter(citizen -> "Non- Resident".equals(citizen.getResidency()))
                .collect(Collectors.toList());
    }

    //age range -> Made By: Xymond Louisse M. Alcazar || Designed By: Michael Justine R. Baladad
    public List<Citizen> getYoungAdults() {
        citizenList.sort(Citizen::compareTo);//sorts based on names
        return citizenList.stream()
                .filter(citizen -> citizen.getAge() >= 18 && citizen.getAge() <= 30)
                .sorted(Comparator.comparing(Citizen::getAge))
                .collect(Collectors.toList());
    }
    public List<Citizen> getMiddleAgeAdults() {
        citizenList.sort(Citizen::compareTo);//sorts based on names - Alcazar
        return citizenList.stream()
                .filter(citizen -> citizen.getAge() >= 31 && citizen.getAge() <= 45)
                .sorted(Comparator.comparing(Citizen::getAge))
                .collect(Collectors.toList());
    }
    public List<Citizen> getOldAdults() {
        citizenList.sort(Citizen::compareTo);//sorts based on names
        return citizenList.stream()
                .filter(citizen -> citizen.getAge() >= 46 && citizen.getAge() <= 70)
                .sorted(Comparator.comparing(Citizen::getAge))
                .collect(Collectors.toList());
    }

    //sorted districts Made By: Angel Bless A. Consolacion
    public List<Citizen> sortedDistrict() {
        citizenList.sort(Citizen::compareTo);//sorts based on names - Alcazar
        return citizenList.stream()
                .sorted(Comparator.comparing(Citizen::getDistrict))
                .collect(Collectors.toList());
    }



//    //Citizen Info - I can't make the search name produce a table.
//                        I will give it up and make it a bunch of JLabels and JTextFields instead. - Alcazar

//    public JTable citizenInfoTable(String name) {
//
//        JTable citizenInfoTable = buildCitizenInfoTable(name);
//
//        //Resizes the column widths (By: Xymond Louisse M. Alcazar
//        citizenInfoTable.getColumnModel().getColumn(0).setPreferredWidth(200);
//        citizenInfoTable.getColumnModel().getColumn(1).setPreferredWidth(350);
//        citizenInfoTable.getColumnModel().getColumn(2).setPreferredWidth(250);
//        citizenInfoTable.getColumnModel().getColumn(3).setPreferredWidth(50);
//        citizenInfoTable.getColumnModel().getColumn(4).setPreferredWidth(100);
//        citizenInfoTable.getColumnModel().getColumn(5).setPreferredWidth(50);
//        citizenInfoTable.getColumnModel().getColumn(6).setPreferredWidth(50);
//
//        //To make cells center alignment (By: Xymond Louisse M. Alcazar)
//        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
//        centerAlignment.setHorizontalAlignment(JLabel.CENTER);
//
//        citizenInfoTable.getColumnModel().getColumn(3).setCellRenderer(centerAlignment);
//        citizenInfoTable.getColumnModel().getColumn(5).setCellRenderer(centerAlignment);
//        citizenInfoTable.getColumnModel().getColumn(6).setCellRenderer(centerAlignment);
//
//        citizenInfoTable.setEnabled(false);
//
//        return citizenInfoTable;
//    }
//    private JTable buildCitizenInfoTable(String searchName) {
//        ArrayList<Citizen> arrayList = new ArrayList<>(citizenList);
//
//        Object[] columnNames = {"Name","Email","Address","Age","Residency","District","Gender"};
//        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
//
//        for (Citizen citizens : arrayList) {
//            if (searchName.equalsIgnoreCase(citizens.getFullName())) {
//                Object[] row = {citizens.getFullName(),citizens.getEmail(),citizens.getAddress(),citizens.getAge(),citizens.getResidency(),citizens.getDistrict(),citizens.getGender()};
//                model.addRow(row);
//            }
//        }
//        return new JTable(model);
//    }

}//end of MyProgramUtility class
