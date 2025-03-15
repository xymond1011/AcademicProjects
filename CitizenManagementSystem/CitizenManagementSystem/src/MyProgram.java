/**
 Notes By: Xymond Louisse M. Alcazar
    I am done with all the outputs of the program. The least effort that I have give is in the part of the last output, the
 Address part, because we don't have enough time to think of a better one that is different from the other outputs we have
 made. That part may result to our score to go down. But at the very least, we have completed the 5 outputs needed.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MyProgram extends JFrame {

    private JPanel mainDisplay, displayPanel, buttonPanel, headPanel;
    private JPanel resMainDisplay, ageMainDisplay;

    //MAIN MENU COMPONENTS
    private JButton residencyButton, ageRangeButton, districtButton, searchNameButton, countAddressButton, exitButton;

    //RESIDENCY COMPONENTS
    private JButton backFilterResidencyButton, exitFilterResidencyButton,residentButton
            , nonResidentButton, backResidentButton, backNonResidentButton, saveToFileResidentButton, saveToFileNonResidentButton;

    //AGE RANGE COMPONENTS
    private JButton backAgeRangeButton, exitAgeRangeButton, backYoungAdultButton, backMiddleAgeButton, backOldAdultButton,
            youngAdultButton, middleAgesButton, oldAdultButton, saveToFileYoungAdultRangeButton,
            saveToFileMiddleAgeRangeButton, saveToFileOldAdultRangeButton;

    //DISTRICT COMPONENTS
    private JButton saveToFileDistrictButton, backDistrictButton, exitDistrictButton;

    //SEARCH CITIZEN COMPONENTS
    private JLabel error;
    private JTextField searchCitizenTextField, specifiedCitizenName, specifiedCitizenEmail, specifiedCitizenAddress,
            specifiedCitizenAge, specifiedCitizenResidency, specifiedCitizenDistrict, specifiedCitizenGender;
    private JButton backCitizenInfoButton, exitCitizenInfoButton, searchCitizenButton;

    //ADDRESS COUNTER COMPONENTS
    private JButton backAddressButton, exitAddressButton;
    private JTextField resultCountApartmentAddressTextField, resultCountHomeAddressTextField, resultCountPostOfficeAddressTextField;

    //ERROR DISPLAY
    private JLabel errorDisplay = new JLabel();

    //BUTTON FUNCTION
    private ButtonsHandler buttonsHandler = new ButtonsHandler();

    //UTILITY AND CUSTOM FONT
    MyProgramUtility utility = new MyProgramUtility();
    Font customFont; float buttonFont = 13f;

    public static void main(String[] args) {
        MyProgram myGUI = new MyProgram();
    }

    public MyProgram() {
        //Custom Font creation (By: Xymond Louisse M. Alcazar)
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/zh-cn.ttf"));
            ge.registerFont(customFont);
        } catch (IOException|FontFormatException e) {
            throw new RuntimeException();
        }

        //Made By: Xymond Louisse M. Alcazar
        try {
            utility.fileReader("res/data.csv");
        } catch (FileNotFoundException e) {
            errorDisplay.setText("\s\sFile not found.");
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        //Made By: John Andrew Angcaway || Modified By: Xymond Louisse M. Alcazar
        setTitle("Citizen List Application");
        setPreferredSize(new Dimension(1000, 500));

        headPanel = new JPanel();
        headPanel.setLayout(new GridLayout(3,0));
        headPanel.setPreferredSize(new Dimension(0,100));
        JLabel textArea = new JLabel();
        textArea.setText("\s\s\sDescription: This application reads, displays, sorts, filters and saves file of an inserted citizen list csv data.");
        textArea.setFont(customFont.deriveFont(12f));

        JLabel tableTitle = new JLabel();
        tableTitle.setText("CITIZEN LIST");
        tableTitle.setFont(customFont.deriveFont(20f));
        tableTitle.setHorizontalAlignment(JLabel.CENTER);

        errorDisplay.setFont(customFont.deriveFont(15f));
        errorDisplay.setForeground(Color.red);
        headPanel.add(textArea);
        headPanel.add(errorDisplay);
        headPanel.add(tableTitle);

        displayPanel = new JPanel();
        setDisplayPanel(displayPanel);

        mainDisplay = new JPanel(new CardLayout());
        mainDisplay.add(displayPanel, "mainMenu");
        mainDisplay.add(residencyMainDisplay(), "residency");
        mainDisplay.add(ageRangeMainDisplay(),"ageRange");
        mainDisplay.add(sortDistrictPanel(), "district");
        mainDisplay.add(searchCitizenPanel(), "citizen");
        mainDisplay.add(countCitizenInAddressPanel(),"address");

        Container pane = getContentPane();
        pane.setLayout(new GridLayout(1,0));
        pane.add(mainDisplay);
        pack();
        setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setDisplayPanel(JPanel panel) {

        buttonPanel = new JPanel();
        setMenuButtonPanel(buttonPanel);
        buttonPanel.setPreferredSize(new Dimension(0,50));

        JScrollPane defaultTable = new JScrollPane(utility.populateTable());

        panel.setLayout(new BorderLayout());
        panel.add(headPanel,BorderLayout.NORTH);
        panel.add(defaultTable,BorderLayout.CENTER);
        panel.add(buttonPanel,BorderLayout.SOUTH);
    }

//OUTPUT PANELS
    //RESIDENCY PANEL -> Made By: Xymond Louisse M. Alcazar
    public JPanel residencyMainDisplay() {
        resMainDisplay = new JPanel(new CardLayout());
        resMainDisplay.add(residencyPanel(), "residencyMenu");
        resMainDisplay.add(residentsPanel(), "residents");
        resMainDisplay.add(nonResidentsPanel(), "nonResidents");

        return resMainDisplay;
    }
    public JPanel residencyPanel() {
        //blanks
        JLabel blank1 = new JLabel(), blank2 = new JLabel(), blank3 = new JLabel()
                ,blank4 = new JLabel(), blank5 = new JLabel(), blank6 = new JLabel();

        //Menu for Filter Residency
        JLabel selectionInstruction = new JLabel();
        selectionInstruction.setText("Press the button to show the specific data");
        selectionInstruction.setFont(customFont.deriveFont(15f));
        residentButton = new JButton("Residents");
        residentButton.addActionListener(buttonsHandler);
        residentButton.setFont(customFont.deriveFont(buttonFont));
        residentButton.setPreferredSize(new Dimension(0,60));

        nonResidentButton = new JButton("Non-residents");
        nonResidentButton.addActionListener(buttonsHandler);
        nonResidentButton.setFont(customFont.deriveFont(buttonFont));
        nonResidentButton.setPreferredSize(new Dimension(0,60));

        //Selection Buttons
        JPanel residencySelection = new JPanel(new GridLayout(3,3));
        residencySelection.add(blank1);
        residencySelection.add(selectionInstruction);
        residencySelection.add(blank2);
        residencySelection.add(blank3);
        residencySelection.add(blank4);
        residencySelection.add(blank5);
        residencySelection.add(residentButton);
        residencySelection.add(blank6);
        residencySelection.add(nonResidentButton);

        //to center the Selection Buttons
        JPanel flowButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,250));
        flowButtonPanel.add(residencySelection);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(0,50));
        setFilterResidencyButtonPanel(buttonPanel);

        JPanel resPanel = new JPanel(new BorderLayout());
        resPanel.add(flowButtonPanel, BorderLayout.CENTER);
        resPanel.add(buttonPanel, BorderLayout.SOUTH);
        return resPanel;
    }
    //Panel for Showing Residents Only (Made By: Xymond Louisse M. Alcazar)
    public JPanel residentsPanel() {
        buttonPanel = new JPanel();
        setResidentButtonPanel(buttonPanel);

        JLabel listTitle = new JLabel(), totalResidents = new JLabel();
        listTitle.setText("RESIDENTS LIST");
        listTitle.setFont(customFont.deriveFont(20f));
        listTitle.setHorizontalAlignment(JLabel.CENTER);

        totalResidents.setText("\s\sTotal Residents: "+utility.getTotalResidency("Resident"));
        totalResidents.setFont(customFont.deriveFont(12f));

        JPanel listHeader = new JPanel(new GridLayout(2,0));
        listHeader.add(listTitle);
        listHeader.add(totalResidents);

        JScrollPane resScrollPane = new JScrollPane(utility.residencyTable("Resident"));
        JPanel resDisplay = new JPanel(new BorderLayout());
        resDisplay.add(listHeader,BorderLayout.NORTH);
        resDisplay.add(resScrollPane,BorderLayout.CENTER);
        resDisplay.add(buttonPanel,BorderLayout.SOUTH);
        return resDisplay;
    }
    //Panel for Showing Non-Residents Only (Made By: Xymond Louisse M. Alcazar)
    public JPanel nonResidentsPanel() {
        buttonPanel = new JPanel();
        setNonResidentButtonPanel(buttonPanel);

        JLabel listTitle = new JLabel(), totalNonResidents = new JLabel();
        listTitle.setText("NON-RESIDENTS LIST");
        listTitle.setFont(customFont.deriveFont(20f));
        listTitle.setHorizontalAlignment(JLabel.CENTER);

        totalNonResidents.setText("\s\sTotal Non-Residents: "+utility.getTotalResidency("Non- Resident"));
        totalNonResidents.setFont(customFont.deriveFont(12f));

        JPanel listHeader = new JPanel(new GridLayout(2,0));
        listHeader.add(listTitle);
        listHeader.add(totalNonResidents);

        JScrollPane resScrollPane = new JScrollPane(utility.residencyTable("Non- Resident"));
        JPanel nonResDisplay = new JPanel(new BorderLayout());
        nonResDisplay.add(listHeader,BorderLayout.NORTH);
        nonResDisplay.add(resScrollPane,BorderLayout.CENTER);
        nonResDisplay.add(buttonPanel,BorderLayout.SOUTH);
        return nonResDisplay;
    }

    //AGE RANGE PANEL -> Made By: Xymond Louisse M. Alcazar
    public JPanel ageRangeMainDisplay() {
        ageMainDisplay = new JPanel(new CardLayout());
        ageMainDisplay.add(ageRangePanel(), "ageRangeMenu");
        ageMainDisplay.add(youngAdultPanel(), "youngAdults");
        ageMainDisplay.add(middleAgePanel(), "middleAges");
        ageMainDisplay.add(oldAdultPanel(), "oldAdults");

        return ageMainDisplay;
    }
    public JPanel ageRangePanel() {
        //blanks
        JLabel blank1 = new JLabel(), blank2 = new JLabel(), blank3 = new JLabel()
                ,blank4 = new JLabel(), blank5 = new JLabel();

        //Menu for Filter Age Range
        JLabel selectionInstruction = new JLabel();
        selectionInstruction.setText("Press the button to show the specific data");
        selectionInstruction.setFont(customFont.deriveFont(15f));
        youngAdultButton = new JButton("Young Adults (18-30)");
        youngAdultButton.addActionListener(buttonsHandler);
        youngAdultButton.setFont(customFont.deriveFont(buttonFont));
        youngAdultButton.setPreferredSize(new Dimension(0,60));

        middleAgesButton = new JButton("Middle Age Adults (31-45)");
        middleAgesButton.addActionListener(buttonsHandler);
        middleAgesButton.setFont(customFont.deriveFont(buttonFont));
        middleAgesButton.setPreferredSize(new Dimension(0,60));

        oldAdultButton = new JButton("Old Adults (46-70)");
        oldAdultButton.addActionListener(buttonsHandler);
        oldAdultButton.setFont(customFont.deriveFont(buttonFont));
        oldAdultButton.setPreferredSize(new Dimension(0,60));

        //Selection Buttons
        JPanel residencySelection = new JPanel(new GridLayout(3,3));
        residencySelection.add(blank1);
        residencySelection.add(selectionInstruction);
        residencySelection.add(blank2);
        residencySelection.add(blank3);
        residencySelection.add(blank4);
        residencySelection.add(blank5);
        residencySelection.add(youngAdultButton);
        residencySelection.add(middleAgesButton);
        residencySelection.add(oldAdultButton);

        //to center the Selection Buttons
        JPanel flowButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,250));
        flowButtonPanel.add(residencySelection);

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(0,50));
        setFilterAgeRangeButtonPanel(buttonPanel);

        JPanel agePanel = new JPanel(new BorderLayout());
        agePanel.add(flowButtonPanel, BorderLayout.CENTER);
        agePanel.add(buttonPanel, BorderLayout.SOUTH);
        return agePanel;
    }
    public JPanel youngAdultPanel() {
        buttonPanel = new JPanel();
        setYoungAdultButtonPanel(buttonPanel);

        JLabel listTitle = new JLabel(), totalYoungAdults = new JLabel();
        listTitle.setText("YOUNG ADULTS LIST");
        listTitle.setFont(customFont.deriveFont(20f));
        listTitle.setHorizontalAlignment(JLabel.CENTER);

        totalYoungAdults.setText("\s\sTotal Young Adults: "+utility.getTotalAgeRange("youngAdults"));
        totalYoungAdults.setFont(customFont.deriveFont(12f));

        JPanel listHeader = new JPanel(new GridLayout(2,0));
        listHeader.add(listTitle);
        listHeader.add(totalYoungAdults);

        JScrollPane resScrollPane = new JScrollPane(utility.youngAdultTable());
        JPanel yAdultDisplay = new JPanel(new BorderLayout());
        yAdultDisplay.add(listHeader,BorderLayout.NORTH);
        yAdultDisplay.add(resScrollPane,BorderLayout.CENTER);
        yAdultDisplay.add(buttonPanel,BorderLayout.SOUTH);
        return yAdultDisplay;
    }
    public JPanel middleAgePanel() {
        buttonPanel = new JPanel();
        setMiddleAgesButtonPanel(buttonPanel);

        JLabel listTitle = new JLabel(), totalMiddleAges = new JLabel();
        listTitle.setText("MIDDLE AGE ADULTS LIST");
        listTitle.setFont(customFont.deriveFont(20f));
        listTitle.setHorizontalAlignment(JLabel.CENTER);

        totalMiddleAges.setText("\s\sTotal Middle Age Adults: "+utility.getTotalAgeRange("middleAges"));
        totalMiddleAges.setFont(customFont.deriveFont(12f));

        JPanel listHeader = new JPanel(new GridLayout(2,0));
        listHeader.add(listTitle);
        listHeader.add(totalMiddleAges);

        JScrollPane resScrollPane = new JScrollPane(utility.middleAgeTable());
        JPanel mAgeDisplay = new JPanel(new BorderLayout());
        mAgeDisplay.add(listHeader,BorderLayout.NORTH);
        mAgeDisplay.add(resScrollPane,BorderLayout.CENTER);
        mAgeDisplay.add(buttonPanel,BorderLayout.SOUTH);
        return mAgeDisplay;
    }
    public JPanel oldAdultPanel() {
        buttonPanel = new JPanel();
        setOldAdultButtonPanel(buttonPanel);

        JLabel listTitle = new JLabel(), totalOldAdults = new JLabel();
        listTitle.setText("OLD ADULTS LIST");
        listTitle.setFont(customFont.deriveFont(20f));
        listTitle.setHorizontalAlignment(JLabel.CENTER);

        totalOldAdults.setText("\s\sTotal Old Adults: "+utility.getTotalAgeRange("oldAdults"));
        totalOldAdults.setFont(customFont.deriveFont(12f));

        JPanel listHeader = new JPanel(new GridLayout(2,0));
        listHeader.add(listTitle);
        listHeader.add(totalOldAdults);

        JScrollPane resScrollPane = new JScrollPane(utility.oldAdultTable());
        JPanel oAdultDisplay = new JPanel(new BorderLayout());
        oAdultDisplay.add(listHeader,BorderLayout.NORTH);
        oAdultDisplay.add(resScrollPane,BorderLayout.CENTER);
        oAdultDisplay.add(buttonPanel,BorderLayout.SOUTH);

        return oAdultDisplay;
    }

    //SORT DISTRICT -> Made By: Xymond Louisse M. Alcazar
    public JPanel sortDistrictPanel() {
        JLabel title = new JLabel("Citizens per District Number"), blank = new JLabel();
        title.setFont(customFont.deriveFont(20f));
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelTitle = new JPanel(new GridLayout(2,0));
        panelTitle.add(title);
        panelTitle.add(blank);

        buttonPanel = new JPanel();
        setDistrictPanelButton(buttonPanel);

        JScrollPane districtSortScrollable = new JScrollPane(utility.districtTable());
        JPanel distPanel = new JPanel(new BorderLayout());
        distPanel.add(panelTitle, BorderLayout.NORTH);
        distPanel.add(districtSortScrollable, BorderLayout.CENTER);
        distPanel.add(buttonPanel, BorderLayout.SOUTH);

        return distPanel;
    }

    //SEARCH CITIZEN NAME -> Made By: Xymond Louisse M. Alcazar
    public JPanel searchCitizenPanel() {
        JLabel
                instruction = new JLabel("Enter the full name of the person you want to search:"),
                blank1 = new JLabel(),
                resultTitle = new JLabel("Citizen Information"),
                fullNameLabel = new JLabel("Name"),
                emailLabel = new JLabel("Email"),
                addressLabel = new JLabel("Address"),
                ageLabel = new JLabel("Age"),
                residencyLabel = new JLabel("Residency"),
                districtLabel = new JLabel("District"),
                genderLabel = new JLabel("Gender");
        resultTitle.setHorizontalAlignment(JLabel.CENTER);

        fullNameLabel.setFont(customFont.deriveFont(15f));
        fullNameLabel.setHorizontalAlignment(JLabel.CENTER);
        emailLabel.setFont(customFont.deriveFont(15f));
        emailLabel.setHorizontalAlignment(JLabel.CENTER);
        addressLabel.setFont(customFont.deriveFont(15f));
        addressLabel.setHorizontalAlignment(JLabel.CENTER);
        ageLabel.setFont(customFont.deriveFont(15f));
        ageLabel.setHorizontalAlignment(JLabel.CENTER);
        residencyLabel.setFont(customFont.deriveFont(15f));
        residencyLabel.setHorizontalAlignment(JLabel.CENTER);
        districtLabel.setFont(customFont.deriveFont(15f));
        districtLabel.setHorizontalAlignment(JLabel.CENTER);
        genderLabel.setFont(customFont.deriveFont(15f));
        genderLabel.setHorizontalAlignment(JLabel.CENTER);

        error = new JLabel();
        error.setFont(customFont.deriveFont(15f));
        error.setForeground(Color.red);

        float textFieldFontSize = 15f;
        resultTitle.setFont(customFont.deriveFont(15f));

        instruction.setFont(customFont.deriveFont(15f));
        instruction.setHorizontalAlignment(JLabel.CENTER);

        searchCitizenTextField = new JTextField();
        searchCitizenTextField.setFont(customFont.deriveFont(15f));
        searchCitizenTextField.setPreferredSize(new Dimension(300,50));

        searchCitizenButton = new JButton("Search");
        searchCitizenButton.setPreferredSize(new Dimension(300,50));
        searchCitizenButton.setFont(customFont.deriveFont(buttonFont));
        searchCitizenButton.addActionListener(buttonsHandler);


        specifiedCitizenName = new JTextField();
        specifiedCitizenName.setFont(customFont.deriveFont(textFieldFontSize));
        specifiedCitizenName.setPreferredSize(new Dimension(300,50));
        specifiedCitizenName.setHorizontalAlignment(SwingConstants.CENTER);
        specifiedCitizenName.setEnabled(false);
        specifiedCitizenName.setDisabledTextColor(Color.black);
        specifiedCitizenEmail = new JTextField();
        specifiedCitizenEmail.setFont(customFont.deriveFont(15f));
        specifiedCitizenEmail.setPreferredSize(new Dimension(300,50));
        specifiedCitizenEmail.setHorizontalAlignment(SwingConstants.CENTER);
        specifiedCitizenEmail.setEnabled(false);
        specifiedCitizenEmail.setDisabledTextColor(Color.black);
        specifiedCitizenAddress = new JTextField();
        specifiedCitizenAddress.setFont(customFont.deriveFont(15f));
        specifiedCitizenAddress.setPreferredSize(new Dimension(300,50));
        specifiedCitizenAddress.setHorizontalAlignment(SwingConstants.CENTER);
        specifiedCitizenAddress.setEnabled(false);
        specifiedCitizenAddress.setDisabledTextColor(Color.black);
        specifiedCitizenAge = new JTextField();
        specifiedCitizenAge.setFont(customFont.deriveFont(textFieldFontSize));
        specifiedCitizenAge.setPreferredSize(new Dimension(300,50));
        specifiedCitizenAge.setHorizontalAlignment(SwingConstants.CENTER);
        specifiedCitizenAge.setEnabled(false);
        specifiedCitizenAge.setDisabledTextColor(Color.black);
        specifiedCitizenResidency = new JTextField();
        specifiedCitizenResidency.setFont(customFont.deriveFont(textFieldFontSize));
        specifiedCitizenResidency.setPreferredSize(new Dimension(300,50));
        specifiedCitizenResidency.setHorizontalAlignment(SwingConstants.CENTER);
        specifiedCitizenResidency.setEnabled(false);
        specifiedCitizenResidency.setDisabledTextColor(Color.black);
        specifiedCitizenDistrict = new JTextField();
        specifiedCitizenDistrict.setFont(customFont.deriveFont(textFieldFontSize));
        specifiedCitizenDistrict.setPreferredSize(new Dimension(300,50));
        specifiedCitizenDistrict.setHorizontalAlignment(SwingConstants.CENTER);
        specifiedCitizenDistrict.setEnabled(false);
        specifiedCitizenDistrict.setDisabledTextColor(Color.black);
        specifiedCitizenGender = new JTextField();
        specifiedCitizenGender.setFont(customFont.deriveFont(textFieldFontSize));
        specifiedCitizenGender.setPreferredSize(new Dimension(300,50));
        specifiedCitizenGender.setHorizontalAlignment(SwingConstants.CENTER);
        specifiedCitizenGender.setEnabled(false);
        specifiedCitizenGender.setDisabledTextColor(Color.black);

        JScrollPane emailScrollbar = new JScrollPane(specifiedCitizenEmail);
        emailScrollbar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JScrollPane addressScrollbar = new JScrollPane(specifiedCitizenAddress);
        addressScrollbar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Search Bar
        JPanel searchBarGrid = new JPanel(new GridLayout(2,2));
        searchBarGrid.add(instruction);
        searchBarGrid.add(blank1);
        searchBarGrid.add(searchCitizenTextField);
        searchBarGrid.add(searchCitizenButton);
        JPanel searchBarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,150));
        searchBarPanel.add(searchBarGrid);

        //Result Bar
        JPanel tableTitlePanel = new JPanel(new GridLayout(0,7));
        tableTitlePanel.add(fullNameLabel);
        tableTitlePanel.add(emailLabel);
        tableTitlePanel.add(addressLabel);
        tableTitlePanel.add(ageLabel);
        tableTitlePanel.add(residencyLabel);
        tableTitlePanel.add(districtLabel);
        tableTitlePanel.add(genderLabel);

        JPanel tableResultPanel = new JPanel(new GridLayout(0,7));
        tableResultPanel.add(specifiedCitizenName);
        tableResultPanel.add(emailScrollbar);
        tableResultPanel.add(addressScrollbar);
        tableResultPanel.add(specifiedCitizenAge);
        tableResultPanel.add(specifiedCitizenResidency);
        tableResultPanel.add(specifiedCitizenDistrict);
        tableResultPanel.add(specifiedCitizenGender);

        JPanel resultPanel = new JPanel(new GridLayout(4,0));
        resultPanel.add(resultTitle);
        resultPanel.add(error);
        resultPanel.add(tableTitlePanel);
        resultPanel.add(tableResultPanel);

        //Buttons
        buttonPanel = new JPanel();
        setSearchCitizenPanelButton(buttonPanel);

        JPanel citInfoPanel = new JPanel(new BorderLayout());
        citInfoPanel.add(searchBarPanel,BorderLayout.CENTER);
        citInfoPanel.add(resultPanel, BorderLayout.NORTH);
        citInfoPanel.add(buttonPanel, BorderLayout.SOUTH);

        return citInfoPanel;
    }

    //Count Address -> Made By: Xymond Louisse M. Alcazar
        //I can't think of any better idea for this that is not similar to the other outputs.
        //This is kind of my last-ditched effort because we don't have enough time to think of
        //another kind of output.
    public JPanel countCitizenInAddressPanel() {
        JLabel  countTitle = new JLabel("Total count of Citizens per Addresses"),
                apartmentAddressLabel = new JLabel("Apartment (Ap) Address: "),
                postOfficeAddressLabel = new JLabel("Post Office (PO) Address: "),
                homeAddressLabel = new JLabel("Home (#) Addresses : "), blank = new JLabel();
        countTitle.setHorizontalAlignment(JLabel.CENTER);
        countTitle.setFont(customFont.deriveFont(15f));
        JPanel header = new JPanel(new GridLayout(2,0));
        header.add(blank);
        header.add(countTitle);

        resultCountApartmentAddressTextField = new JTextField();
        resultCountHomeAddressTextField = new JTextField();
        resultCountPostOfficeAddressTextField = new JTextField();

        apartmentAddressLabel.setFont(customFont.deriveFont(15f));
        postOfficeAddressLabel.setFont(customFont.deriveFont(15f));
        homeAddressLabel.setFont(customFont.deriveFont(15f));
        resultCountApartmentAddressTextField.setFont(customFont.deriveFont(15f));
        resultCountHomeAddressTextField.setFont(customFont.deriveFont(15f));
        resultCountPostOfficeAddressTextField.setFont(customFont.deriveFont(15f));

        apartmentAddressLabel.setPreferredSize(new Dimension(200, 50));
        postOfficeAddressLabel.setPreferredSize(new Dimension(200, 50));
        homeAddressLabel.setPreferredSize(new Dimension(200, 50));
        resultCountApartmentAddressTextField.setPreferredSize(new Dimension(200, 50));
        resultCountHomeAddressTextField.setPreferredSize(new Dimension(200, 50));
        resultCountPostOfficeAddressTextField.setPreferredSize(new Dimension(200, 50));

        resultCountApartmentAddressTextField.setText(String.valueOf(utility.countCitizenApartmentAddress()));
        resultCountHomeAddressTextField.setText(String.valueOf(utility.countCitizenHomeAddress()));
        resultCountPostOfficeAddressTextField.setText(String.valueOf(utility.countCitizenPostOfficeAddress()));

        resultCountApartmentAddressTextField.setEnabled(false);
        resultCountPostOfficeAddressTextField.setEnabled(false);
        resultCountHomeAddressTextField.setEnabled(false);

        resultCountApartmentAddressTextField.setDisabledTextColor(Color.black);
        resultCountPostOfficeAddressTextField.setDisabledTextColor(Color.black);
        resultCountHomeAddressTextField.setDisabledTextColor(Color.black);


        JPanel resultPanel = new JPanel(new GridLayout(3,2));
        resultPanel.setPreferredSize(new Dimension(550,300));
        resultPanel.add(apartmentAddressLabel);
        resultPanel.add(resultCountApartmentAddressTextField);
        resultPanel.add(homeAddressLabel);
        resultPanel.add(resultCountHomeAddressTextField);
        resultPanel.add(postOfficeAddressLabel);
        resultPanel.add(resultCountPostOfficeAddressTextField);

        JPanel resultFlowLayout = new JPanel(new FlowLayout(FlowLayout.CENTER,0,200));
        resultFlowLayout.add(resultPanel);

        buttonPanel = new JPanel();
        setCountAddressPanelButton(buttonPanel);

        JPanel countCitPanel = new JPanel(new BorderLayout());
        countCitPanel.add(header,BorderLayout.NORTH);
        countCitPanel.add(resultFlowLayout, BorderLayout.CENTER);
        countCitPanel.add(buttonPanel,BorderLayout.SOUTH);

        return countCitPanel;
    }

    //BUTTON PANELS
    //Made By: John Andrew Angcaway | Modified By: Xymond Louisse M. Alcazar
    public void setMenuButtonPanel(JPanel panel) {

        residencyButton = new JButton("Citizen Residency");
        ageRangeButton = new JButton("Citizen Age Range");
        districtButton = new JButton("Sort District Numbers");
        searchNameButton = new JButton("Search Citizen Name");
        countAddressButton = new JButton("Citizens per Addresses");
        exitButton = new JButton("Exit");

        residencyButton.setFont(customFont.deriveFont(buttonFont));
        ageRangeButton.setFont(customFont.deriveFont(buttonFont));
        districtButton.setFont(customFont.deriveFont(buttonFont));
        searchNameButton.setFont(customFont.deriveFont(buttonFont));
        countAddressButton.setFont(customFont.deriveFont(buttonFont));
        exitButton.setFont(customFont.deriveFont(buttonFont));

        residencyButton.addActionListener(buttonsHandler);
        ageRangeButton.addActionListener(buttonsHandler);
        districtButton.addActionListener(buttonsHandler);
        searchNameButton.addActionListener(buttonsHandler);
        countAddressButton.addActionListener(buttonsHandler);
        exitButton.addActionListener(buttonsHandler);
        panel.setLayout(new GridLayout(0, 6));
        panel.add(residencyButton);
        panel.add(ageRangeButton);
        panel.add(districtButton);
        panel.add(searchNameButton);
        panel.add(countAddressButton);
        panel.add(exitButton);
    }
    //Residency Filter -> Made By: Xymond Louisse M. Alcazar
    public void setFilterResidencyButtonPanel(JPanel panel) {
        backFilterResidencyButton = new JButton("Back");
        exitFilterResidencyButton = new JButton("Exit");

        backFilterResidencyButton.setFont(customFont.deriveFont(buttonFont));
        exitFilterResidencyButton.setFont(customFont.deriveFont(buttonFont));

        backFilterResidencyButton.addActionListener(buttonsHandler);
        exitFilterResidencyButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.add(exitFilterResidencyButton);
        panel.add(backFilterResidencyButton);
    }
    //Made By: Xymond Louisse M. Alcazar
    public void setResidentButtonPanel(JPanel panel) {
        backResidentButton = new JButton("Back");
        saveToFileResidentButton = new JButton("Save to File");

        backResidentButton.setFont(customFont.deriveFont(buttonFont));
        saveToFileResidentButton.setFont(customFont.deriveFont(buttonFont));

        backResidentButton.addActionListener(buttonsHandler);
        saveToFileResidentButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.setPreferredSize(new Dimension(0,50));
        panel.add(backResidentButton);
        panel.add(saveToFileResidentButton);
    }
    //Made By: Xymond Louisse M. Alcazar
    public void setNonResidentButtonPanel(JPanel panel) {
        backNonResidentButton = new JButton("Back");
        saveToFileNonResidentButton = new JButton("Save to File");

        backNonResidentButton.setFont(customFont.deriveFont(buttonFont));
        saveToFileNonResidentButton.setFont(customFont.deriveFont(buttonFont));

        backNonResidentButton.addActionListener(buttonsHandler);
        saveToFileNonResidentButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.setPreferredSize(new Dimension(0,50));
        panel.add(backNonResidentButton);
        panel.add(saveToFileNonResidentButton);
    }
    //Ange Range Buttons -> Made By: Xymond Louisse M. Alcazar
    public void setFilterAgeRangeButtonPanel(JPanel panel) {
        backAgeRangeButton = new JButton("Back");
        exitAgeRangeButton = new JButton("Exit");

        backAgeRangeButton.setFont(customFont.deriveFont(buttonFont));
        exitAgeRangeButton.setFont(customFont.deriveFont(buttonFont));

        backAgeRangeButton.addActionListener(buttonsHandler);
        exitAgeRangeButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.add(exitAgeRangeButton);
        panel.add(backAgeRangeButton);
    }
    public void setYoungAdultButtonPanel(JPanel panel) {
        backYoungAdultButton = new JButton("Back");
        saveToFileYoungAdultRangeButton = new JButton("Save to File");

        backYoungAdultButton.setFont(customFont.deriveFont(buttonFont));
        saveToFileYoungAdultRangeButton.setFont(customFont.deriveFont(buttonFont));

        backYoungAdultButton.addActionListener(buttonsHandler);
        saveToFileYoungAdultRangeButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.setPreferredSize(new Dimension(0,50));
        panel.add(backYoungAdultButton);
        panel.add(saveToFileYoungAdultRangeButton);
    }
    public void setMiddleAgesButtonPanel(JPanel panel) {
        backMiddleAgeButton = new JButton("Back");
        saveToFileMiddleAgeRangeButton = new JButton("Save to File");

        backMiddleAgeButton.setFont(customFont.deriveFont(buttonFont));
        saveToFileMiddleAgeRangeButton.setFont(customFont.deriveFont(buttonFont));

        backMiddleAgeButton.addActionListener(buttonsHandler);
        saveToFileMiddleAgeRangeButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.setPreferredSize(new Dimension(0,50));
        panel.add(backMiddleAgeButton);
        panel.add(saveToFileMiddleAgeRangeButton);
    }
    public void setOldAdultButtonPanel(JPanel panel) {
        backOldAdultButton = new JButton("Back");
        saveToFileOldAdultRangeButton = new JButton("Save to File");

        backOldAdultButton.setFont(customFont.deriveFont(buttonFont));
        saveToFileOldAdultRangeButton.setFont(customFont.deriveFont(buttonFont));

        backOldAdultButton.addActionListener(buttonsHandler);
        saveToFileOldAdultRangeButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.setPreferredSize(new Dimension(0,50));
        panel.add(backOldAdultButton);
        panel.add(saveToFileOldAdultRangeButton);
    }

    //Sort District Buttons -> Made By: Xymond Louisse M. Alcazar
    public void setDistrictPanelButton(JPanel panel) {
        backDistrictButton = new JButton("Back");
        exitDistrictButton = new JButton("Exit");
        saveToFileDistrictButton = new JButton("Save to File");

        backDistrictButton.setFont(customFont.deriveFont(buttonFont));
        exitDistrictButton.setFont(customFont.deriveFont(buttonFont));
        saveToFileDistrictButton.setFont(customFont.deriveFont(buttonFont));

        backDistrictButton.setPreferredSize(new Dimension(0,50));
        exitDistrictButton.setPreferredSize(new Dimension(0,50));
        saveToFileDistrictButton.setPreferredSize(new Dimension(0,50));

        backDistrictButton.addActionListener(buttonsHandler);
        exitDistrictButton.addActionListener(buttonsHandler);
        saveToFileDistrictButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,3));
        panel.add(exitDistrictButton);
        panel.add(backDistrictButton);
        panel.add(saveToFileDistrictButton);
    }

    //Citizen Info Buttons -> Made By: Xymond Louisse M. Alcazar
    public void setSearchCitizenPanelButton(JPanel panel) {
        backCitizenInfoButton= new JButton("Back");
        exitCitizenInfoButton = new JButton("Exit");

        backCitizenInfoButton.setFont(customFont.deriveFont(buttonFont));
        exitCitizenInfoButton.setFont(customFont.deriveFont(buttonFont));

        backCitizenInfoButton.setPreferredSize(new Dimension(0,50));
        exitCitizenInfoButton.setPreferredSize(new Dimension(0,50));

        backCitizenInfoButton.addActionListener(buttonsHandler);
        exitCitizenInfoButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.add(exitCitizenInfoButton);
        panel.add(backCitizenInfoButton);
    }

    //Count Address Buttons Made By: Xymond Louisse M. Alcazar
    public void setCountAddressPanelButton(JPanel panel) {
        backAddressButton = new JButton("Back");
        exitAddressButton = new JButton("Exit");

        backAddressButton.setFont(customFont.deriveFont(buttonFont));
        exitAddressButton.setFont(customFont.deriveFont(buttonFont));

        backAddressButton.setPreferredSize(new Dimension(0,50));
        exitAddressButton.setPreferredSize(new Dimension(0,50));

        backAddressButton.addActionListener(buttonsHandler);
        exitAddressButton.addActionListener(buttonsHandler);

        panel.setLayout(new GridLayout(0,2));
        panel.add(exitAddressButton);
        panel.add(backAddressButton);
    }
    private class ButtonsHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

            CardLayout cards = (CardLayout) (mainDisplay.getLayout());

            if (event.getSource() == exitButton) {
                System.exit(0);
            }
            //menu
            if (event.getSource() == residencyButton) {
                cards.show(mainDisplay,"residency");
            }
            if (event.getSource() == ageRangeButton) {
                cards.show(mainDisplay,"ageRange");
            }
            if (event.getSource() == districtButton) {
                cards.show(mainDisplay,"district");
            }
            if (event.getSource() == searchNameButton) {
                cards.show(mainDisplay,"citizen");
            }
            if (event.getSource() == countAddressButton) {
                cards.show(mainDisplay, "address");
            }

        //Made By: Xymond Louisse M. Alcazar
            //residencyPanel
            if(event.getSource() == backFilterResidencyButton) {
                cards.show(mainDisplay,"mainMenu");
            }
            if(event.getSource() == exitFilterResidencyButton) {
                System.exit(0);
            }

            CardLayout residencyCard = (CardLayout) (resMainDisplay.getLayout());
            //selectResidency
            if(event.getSource() == backResidentButton) {
                residencyCard.show(resMainDisplay,"residencyMenu");
            }
            if(event.getSource() == backNonResidentButton) {
                residencyCard.show(resMainDisplay,"residencyMenu");
            }
            if(event.getSource() == residentButton) {
                residencyCard.show(resMainDisplay,"residents");
            }
            if(event.getSource() == nonResidentButton) {
                residencyCard.show(resMainDisplay,"nonResidents");
            }

            //SaveToFile -> Made By: Michael Justine R. Baladad || Helped By: Angel Bless A. Consolacion
            if(event.getSource() == saveToFileResidentButton) {
                File filteredFileByResidency = new File("res/List of Residents.txt");
                try (PrintWriter saveFile = new PrintWriter(new FileWriter(filteredFileByResidency))) {
                    saveFile.printf("%-25s%-65s%-45s%-15s%-20s%-15s%-10s%n","Name","Email","Address","Age","Residency","District","Gender" );
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    for (Citizen citizen : utility.getResidents()) { // Only write residents to the file
                        saveFile.println(citizen.toString());
                    }
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    JOptionPane.showMessageDialog(null, "List of Residents has saved to file.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving list of residents: " + e.getMessage());
                }
            }
            if(event.getSource() == saveToFileNonResidentButton) {
                File filteredFileByResidency = new File("res/List of Non-Residents.txt");
                try (PrintWriter saveFile = new PrintWriter(new FileWriter(filteredFileByResidency))) {
                    saveFile.printf("%-25s%-65s%-45s%-15s%-20s%-15s%-10s%n","Name","Email","Address","Age","Residency","District","Gender" );
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    for (Citizen citizen : utility.getNonResidents()) { // Only write non-residents to the file
                        saveFile.println(citizen.toString());
                    }
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    JOptionPane.showMessageDialog(null, "List of Non-Residents has saved to file.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving List of Non-Residents: " + e.getMessage());
                }
            }

            //age range Made By: Xymond Louisse M. Alcazar
            if(event.getSource() == backAgeRangeButton) {
                cards.show(mainDisplay,"mainMenu");
            }
            if(event.getSource() == exitAgeRangeButton) {
                System.exit(0);
            }

            CardLayout ageCard = (CardLayout) (ageMainDisplay.getLayout());
            //selectAgeRange
            if(event.getSource() == backYoungAdultButton) {
                ageCard.show(ageMainDisplay,"ageRangeMenu");
            }
            if(event.getSource() == backMiddleAgeButton) {
                ageCard.show(ageMainDisplay,"ageRangeMenu");
            }
            if(event.getSource() == backOldAdultButton) {
                ageCard.show(ageMainDisplay,"ageRangeMenu");
            }
            if(event.getSource() == youngAdultButton) {
                ageCard.show(ageMainDisplay,"youngAdults");
            }
            if(event.getSource() == middleAgesButton) {
                ageCard.show(ageMainDisplay,"middleAges");
            }
            if(event.getSource() == oldAdultButton) {
                ageCard.show(ageMainDisplay,"oldAdults");
            }

            //SaveToFile -> Made By: Xymond Louisse M. Alcazar || Designed By: Michael Justine R. Baladad
            if(event.getSource() == saveToFileYoungAdultRangeButton) {
                File filteredFileByAgeRange = new File("res/List of Young Adults.txt");
                try (PrintWriter saveFile = new PrintWriter(new FileWriter(filteredFileByAgeRange))) {
                    saveFile.printf("%-25s%-65s%-45s%-15s%-20s%-15s%-10s%n","Name","Email","Address","Age","Residency","District","Gender" );
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    for (Citizen citizen : utility.getYoungAdults()) { // Only write non-residents to the file
                        saveFile.println(citizen.toString());
                    }
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    JOptionPane.showMessageDialog(null, "List of Young Adults has saved to file.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving List of Young Adults: " + e.getMessage());
                }
            }
            if(event.getSource() == saveToFileMiddleAgeRangeButton) {
                File filteredFileByAgeRange = new File("res/List of Middle Age Adults.txt");
                try (PrintWriter saveFile = new PrintWriter(new FileWriter(filteredFileByAgeRange))) {
                    saveFile.printf("%-25s%-65s%-45s%-15s%-20s%-15s%-10s%n","Name","Email","Address","Age","Residency","District","Gender" );
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    for (Citizen citizen : utility.getMiddleAgeAdults()) { // Only write non-residents to the file
                        saveFile.println(citizen.toString());
                    }
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    JOptionPane.showMessageDialog(null, "List of Middle Age Adults has saved to file.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving List of Middle Age Adults: " + e.getMessage());
                }
            }
            if(event.getSource() == saveToFileOldAdultRangeButton) {
                File filteredFileByAgeRange = new File("res/List of Old Adults.txt");
                try (PrintWriter saveFile = new PrintWriter(new FileWriter(filteredFileByAgeRange))) {
                    saveFile.printf("%-25s%-65s%-45s%-15s%-20s%-15s%-10s%n","Name","Email","Address","Age","Residency","District","Gender" );
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    for (Citizen citizen : utility.getOldAdults()) { // Only write non-residents to the file
                        saveFile.println(citizen.toString());
                    }
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    JOptionPane.showMessageDialog(null, "List of Old Adults has saved to file.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving List of Old Adults: " + e.getMessage());
                }
            }

            //District
            if(event.getSource() == backDistrictButton) {
                cards.show(mainDisplay,"mainMenu");
            }
            if(event.getSource() == exitDistrictButton) {
                System.exit(0);
            }
            //SaveToFile -> Made By: Angel Bless Consolacion || Simplified By: Xymond Louisse M. Alcazar || Designed By: Michael Justine R. Baladad
            if(event.getSource() == saveToFileDistrictButton) {
                File sortedFileByDistrict = new File("res/List of Citizens per District.txt");
                try (PrintWriter saveFile = new PrintWriter(new FileWriter(sortedFileByDistrict))) {
                    saveFile.printf("%-25s%-65s%-45s%-15s%-20s%-15s%-10s%n","Name","Email","Address","Age","Residency","District","Gender" );

                    int currentDistrict = 0, districtNumber = 1;
                    for (Citizen citizen : utility.sortedDistrict()) {
                        if(currentDistrict != citizen.getDistrict()) {
                            saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            saveFile.println("DISTRICT "+districtNumber);
                            saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                            currentDistrict = citizen.getDistrict();
                            districtNumber++;
                        }
                        saveFile.println(citizen.toString());
                    }
                    saveFile.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    JOptionPane.showMessageDialog(null, "List of Citizens per District has saved to file.");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error saving List of Citizens per District: " + e.getMessage());
                }
            }

            //Citizen -> Made By: Xymond Louisse M. Alcazar
            if(event.getSource() == backCitizenInfoButton) {
                cards.show(mainDisplay,"mainMenu");
            }
            if(event.getSource() == exitCitizenInfoButton) {
                System.exit(0);
            }

            //Search Citizen -> Made By: John Andrew Angcaway
            if(event.getSource() == searchCitizenButton) {
                String searchName = searchCitizenTextField.getText().trim();

                if (searchName.isEmpty()) {
                    error.setText("No specified entry.");
                } else {
                    boolean found = false;
                    for (Citizen citizen : utility.citizenList) {
                        String fullName = citizen.getFullName().trim();
                        if (searchName.equalsIgnoreCase(fullName)) {
                            specifiedCitizenName.setText(citizen.getFullName());
                            specifiedCitizenEmail.setText(citizen.getEmail());
                            specifiedCitizenAddress.setText(citizen.getAddress());
                            specifiedCitizenAge.setText(String.valueOf(citizen.getAge()));
                            specifiedCitizenResidency.setText(citizen.getResidency());
                            specifiedCitizenDistrict.setText(String.valueOf(citizen.getDistrict()));
                            specifiedCitizenGender.setText(String.valueOf(citizen.getGender()));

                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        error.setText("No matching name found.");
                    }
                }
            }

            //Address Made By: Xymond Louisse M. Alcazar
            if(event.getSource() == backAddressButton) {
                cards.show(mainDisplay,"mainMenu");
            }
            if(event.getSource() == exitAddressButton) {
                System.exit(0);
            }
        }
    }//end of ButtonsHandler class
}//end of MyProgram class
