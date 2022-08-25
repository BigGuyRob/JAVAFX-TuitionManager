package application;

import tuition.*;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

/**
 * TuitionGuiController class Contains logic related to interacting with roster
 * and students within a roster and the GUI
 * 
 * @author Robert Reid, Anthony Romanushko
 */
public class TuitionGuiController {
    /**
     * roster that contains student information
     */
    private Roster roster = new Roster();
    /** this is the identifier for the add student button */
    @FXML
    private Button btnAddStudent;
    /** this is the identifier for the remove student button */
    @FXML
    private Button btnRemoveStudent;
    /** this is the identifier for the first pane student name field */
    @FXML
    private TextField txtStudentName;
    /** this is the identifier for the first pane credit hours field */
    @FXML
    private TextField txtCredits;
    /** this is the identifier for the output area */
    @FXML
    private TextArea txtOutputArea;

    /**
     * this is the identifier for the first pane major radio button control group
     */
    @FXML
    private ToggleGroup Majors;
    /** this is the identifier for the first pane CS major radio button */
    @FXML
    private RadioButton rdbCS;
    /** this is the identifier for the first pane EE major radio button */
    @FXML
    private RadioButton rdbEE;
    /** this is the identifier for the first pane ME major radio button */
    @FXML
    private RadioButton rdbME;
    /** this is the identifier for the first pane IT major radio button */
    @FXML
    private RadioButton rdbIT;
    /** this is the identifier for the first pane BA major radio button */
    @FXML
    private RadioButton rdbBA;

    /**
     * this is the identifier for the first pane resident status radio button
     * control group
     */
    @FXML
    private ToggleGroup ResidentStatus;
    /** this is the identifier for the first pane Resident radio button */
    @FXML
    private RadioButton rdbRes;
    /** this is the identifier for the first pane NonResident radio button */
    @FXML
    private RadioButton rdbNonRes;

    /**
     * this is the identifier for the first pane NonResident status radio button
     * control group
     */
    @FXML
    private ToggleGroup NonResidentStatus;
    /** this is the identifier for the first pane NY radio button */
    @FXML
    private RadioButton rdbNY;
    /** this is the identifier for the first pane CT radio button */
    @FXML
    private RadioButton rdbCT;
    /** this is the identifier for the first pane International radio button */
    @FXML
    private RadioButton rdbInt;
    /** this is the identifier for the first pane SAS toggle button */
    @FXML
    private ToggleButton tgSAS;

    /**
     * this is the identifier for the second pane Major radio button control group
     */
    @FXML
    private ToggleGroup PaymentMajor;
    /** this is the identifier for the second pane CS radio button */
    @FXML
    private RadioButton rdbCSP;
    /** this is the identifier for the second pane EE radio button */
    @FXML
    private RadioButton rdbEEP;
    /** this is the identifier for the second pane ME radio button */
    @FXML
    private RadioButton rdbMEP;
    /** this is the identifier for the second pane IT radio button */
    @FXML
    private RadioButton rdbITP;
    /** this is the identifier for the second pane BA radio button */
    @FXML
    private RadioButton rdbBAP;
    /** this is the identifier for the second pane Find button */
    @FXML
    private Button btnFind;

    /** this is the identifier for the second pane Status Label */
    @FXML
    private Label lblStatus;
    /** this is the identifier for the second pane Current Status Label */
    @FXML
    private Label lblCurrStatus;
    /** this is the identifier for the second pane SAS button */
    @FXML
    private ToggleButton btnchangeSAS;
    /** this is the identifier for the second pane Student Name Textfield */
    @FXML
    private TextField txtStudentNamepayments;
    /** this is the identifier for the second pane Financial Aid Eligible Label */
    @FXML
    private Label lblFinAidEligible;
    /** this is the identifier for the second pane Find Label */
    @FXML
    private Label lblFinds;
    /** this is the identifier for the second pane Financial Aid Text Box */
    @FXML
    private TextField txtFinAid;
    /** this is the identifier for the second pane pay Financial Aid Button */
    @FXML
    private Button btnPayFinAid;
    /** this is the identifier for the second pane pay Tuition Button */
    @FXML
    private Button btnPayT;
    /** this is the identifier for the second pane payment amount text field */
    @FXML
    private TextField txtPaymentamt;
    /** this is the identifier for the second pane date picker */
    @FXML
    private DatePicker dpDatePicker;
    // We're going to check for invalid characters in the student name
    /** student that is selected for modifying information of */
    private Student focus = null;

    /**
     * Calls the method which Adds a student to the roster taking in user data from
     * the GUI
     * 
     * @param Event - on btnAddStudent button click
     */
    @FXML
    private void AddStudent(ActionEvent Event) {
        String errorMessage = validateStudentInfoEntered();
        if (!(errorMessage.equals(""))) {
            txtOutputArea.appendText("\n" + errorMessage);
        } else {
            String[] student = createStudent();
            txtOutputArea.appendText("\n" + execCommand(student));
        }
    }

    /**
     * Calls the method which Removes a student if they exist in the roster taking
     * in user data from GUI
     * 
     * @param Event - on btnRemoveStudent button click
     */
    @FXML
    private void RemoveStudent(ActionEvent Event) {
        String errorMessage = validateStudentRemovedInfoEntered();
        if (!(errorMessage.equals(""))) {
            txtOutputArea.appendText("\n" + errorMessage);
        } else {
            String[] student = cmdStudent("R");
            txtOutputArea.appendText("\n" + execCommand(student));
        }
    }

    /**
     * Calls the method to calculate the tuition due for the target student if they
     * are in the roster
     * 
     * @param Event - on btnTuitionDue button click
     */
    @FXML
    private void tuitionDue(ActionEvent Event) {
        String errorMessage = validateStudentRemovedInfoEntered();
        if (!(errorMessage.equals(""))) {
            txtOutputArea.appendText("\n" + errorMessage);
        } else {
            txtOutputArea.appendText(
                    "\n" + roster.calcSingle(new Student(txtStudentName.getText(), parseMajor(getMajor()))));
        }
    }

    /**
     * Calls the method to calculate the tuition due for all students in the roster
     * if the roster is not empty
     * 
     * @param Event - on btnCalculateTuitionDue button click
     */
    @FXML
    private void cTuition(ActionEvent Event) {
        txtOutputArea.appendText("\n" + calculateTuition());
    }

    /**
     * Calls the method which prints the roster sorted by name if roster is not
     * empty
     * 
     * @param Event - on btnPrintRosterBN button click
     */
    @FXML
    private void pbName(ActionEvent Event) {
        txtOutputArea.appendText("\n" + printRosterBN());
    }

    /**
     * Calls the method which prints the roster sorted by payment date if roster is
     * not empty
     * 
     * @param Event - on btnPrintRosterBPD button click
     */
    @FXML
    private void pbDate(ActionEvent Event) {
        txtOutputArea.appendText("\n" + printRosterBPD());
    }

    /**
     * Prints the current order of the roster if the roster is not empty
     * 
     * @param Event - on btnPrintRoster button click
     */
    @FXML
    private void pbCurrentOrder(ActionEvent Event) {
        txtOutputArea.appendText("\n" + printRoster());
    }

    /**
     * Sets the focus student for the payment options tab if student is found and
     * shows the appropriate options
     * 
     * @param Event - on btnFind button click
     */
    @FXML
    private void find(ActionEvent Event) {
        String errorMessage = validateStudentPaymentInfoEntered();
        if (!(errorMessage.equals(""))) {
            txtOutputArea.appendText("\n" + errorMessage);
        } else {
            Student student = new Student(txtStudentNamepayments.getText(), parseMajor(getMajorPayments()));
            if (roster.getStudent(student) != null) {
                Student actualStudent = roster.getStudent(student);
                txtOutputArea.appendText("\n" + "Student found: " + actualStudent.toString());
                focus = actualStudent;
                if (actualStudent instanceof International) {
                    addChangeSASOptions(true);
                    addPayFinAidOptions(false);
                } else if (actualStudent instanceof Resident) {
                    addChangeSASOptions(false);
                    if (actualStudent.checkFullTime()) {
                        addPayFinAidOptions(true);
                    } else {
                        addPayFinAidOptions(false);
                    }
                } else {
                    addChangeSASOptions(false);
                    addPayFinAidOptions(false);
                }
            } else {
                txtOutputArea.appendText("\n" + "Student not found.");
            }
        }
    }

    /**
     * Calls the method which pays tuition for the focus student after checking
     * correct user input
     * 
     * @param Event - on btnPayTuition button click
     */
    @FXML
    private void payTuition(ActionEvent Event) {
        double paymentAmount;

        if (focus != null) {
            String[] student = cmdStudent("T");
            try {
                paymentAmount = Double.parseDouble(txtPaymentamt.getText());
                txtOutputArea.appendText("\n" + execCommand(student));
                // for(String x : student) {System.out.printf("%s\n",x);}
                txtOutputArea.appendText("\n for:" + focus.toString());
            } catch (NumberFormatException e) {
                txtOutputArea.appendText("\n" + "Invalid payment amount entered.");
            }
        } else {
            txtOutputArea.appendText("\n" + "Please find student first.");
        }
    }

    /**
     * Calls the method which sets the financial aid amount for resident students
     * after checking correct user input
     * 
     * @param Event - on btnPayFinAid button click
     */
    @FXML
    private void payFinAid(ActionEvent Event) {
        double finAid;
        if (focus != null) {
            String[] student = cmdStudent("F");
            try {
                finAid = Double.parseDouble(txtFinAid.getText());
                txtOutputArea.appendText("\n" + execCommand(student));
                txtOutputArea.appendText("\n for:" + focus.toString());
            } catch (NumberFormatException e) {
                txtOutputArea.appendText("\n" + "Invalid financial aid amount entered.");
            }
        } else {
            txtOutputArea.appendText("\n" + "Please find student first.");
        }
    }

    /**
     * Calls the method which sets the SAS status of an international student after
     * checking correct user input
     * 
     * @param Event - btnchangeSAS button toggle changed
     */
    @FXML
    private void changeSASStatus(ActionEvent Event) {
        if (focus != null) {
            String[] student = cmdStudent("S");
            txtOutputArea.appendText("\n" + execCommand(student));
        } else {
            txtOutputArea.appendText("\n" + "Please find student first.");
        }
    }

    /**
     * method that resolves user commands and then executes them if valid, returns
     * operation status of command afterwards
     * 
     * @param arr rawInput from the parseCommand method
     * @return retMes returned message to the user
     */
    public String execCommand(String[] arr) {
        String retMes = "";
        String command = arr[0];
        if (command.length() == 0) {
            return retMes;
        }
        switch (command) {
        case "AR":
            retMes = addResident(arr);
            break;
        case "AN":
            retMes = addNonResident(arr);
            break;
        case "AT":
            retMes = addTristate(arr);
            break;
        case "AI":
            retMes = addInternational(arr);
            break;
        case "R":
            retMes = removeStudent(arr);
            break;
        case "C":
            retMes = calculateTuition();
            break;
        case "T":
            retMes = payT(arr);
            break;
        case "S":
            retMes = setSASTrue(arr);
            break;
        case "F":
            retMes = setFinAid(arr);
            break;
        case "P":
            retMes = printRoster();
            break;
        case "PN":
            retMes = printRosterBN();
            break;
        case "PT":
            retMes = printRosterBPD();
            break;
        default:
            retMes = "Command '" + command + "' not supported!";
            break;
        }
        return retMes;
    }

    /**
     * Validates the StudentInfo entered on the manage Students tab is entered
     * correctly
     * 
     * @return String: errormessage to be displayed if user input is invalid
     */
    public String validateStudentInfoEntered() {
        int credits;
        if (txtStudentName.getText().trim().length() == 0) {
            return "Please enter a student name.";
        }
        try {
            if (Majors.getSelectedToggle().equals(null)) {
                return "All students must declare a major.";
            }
        } catch (NullPointerException e) {
            return "All students must delcare a major.";
        }
        if (ResidentStatus.getSelectedToggle() == null) {
            return "Please declare residency status of student.";
        }
        try {
            credits = Integer.parseInt(txtCredits.getText());
        } catch (NumberFormatException e) {
            return "Number of credits entered incorrectly.";
        }
        return "";
    }

    /**
     * Validates the StudentInfo entered on the manage Students tab is entered
     * correctly for removing a student, which does not require credits to be
     * entered
     * 
     * @return String: errormessage to be displayed if user input is invalid
     */
    public String validateStudentRemovedInfoEntered() {
        if (txtStudentName.getText().trim().length() == 0) {
            return "Please enter a student name.";
        }
        try {
            if (Majors.getSelectedToggle().equals(null)) {
                return "Enter students major.";
            }
        } catch (NullPointerException e) {
            return "Enter students major.";
        }
        return "";
    }

    /**
     * Validates the StudentInfo entered on the pay tution tab is entered correctly
     * for paying tuition only name and major must be entered
     * 
     * @return String: errormessage to be displayed if user input is invalid
     */
    public String validateStudentPaymentInfoEntered() {
        if (txtStudentNamepayments.getText().trim().length() == 0) {
            return "Please enter a student name.";
        }
        try {
            if (PaymentMajor.getSelectedToggle().equals(null)) {
                return "Please specify student major.";
            }
        } catch (NullPointerException e) {
            return "Please specify student major.";
        }
        return "";
    }

    /**
     * Generate a String array with the valid parameters for adding a student to the
     * roster
     * 
     * @return String[] array of string corresponding to {command, name, major,
     *         credits, status} to be handled by execCommand
     */
    private String[] createStudent() {
        String major = getMajor();
        String name = txtStudentName.getText();
        String status = "";
        String credits = txtCredits.getText();
        String command = "";
        if (rdbRes.isSelected()) {
            command = "AR";
        } else {// They are non Resident
            command = "AT";
            if (rdbNY.isSelected()) {
                status = "NY";
            } else if (rdbCT.isSelected()) {
                status = "CT";
            } else if (rdbInt.isSelected()) {
                command = "AI";
                if (tgSAS.isSelected()) {
                    status = "true";
                } else {
                    status = "false";
                }
            } else {
                command = "AN";
                status = "";
            }
        }
        return new String[] { command, name, major, credits, status };
    }

    /**
     * Generate a String array with the valid parameters for manipulating student
     * information
     * 
     * @param command command to be run
     * @return String[] array of string corresponding to {command, name, major,
     *         otherData} to be handled by execCommand depending on command
     */
    private String[] cmdStudent(String command) {
        String name = txtStudentName.getText();
        String major = getMajor();
        if (command == "T") {
            name = txtStudentNamepayments.getText();
            major = getMajorPayments();
            return new String[] { command, name, major, txtPaymentamt.getText() };
        } else if (command == "F") {
            name = txtStudentNamepayments.getText();
            major = getMajorPayments();
            return new String[] { command, name, major, txtFinAid.getText() };
        } else if (command == "S") {
            name = txtStudentNamepayments.getText();
            major = getMajorPayments();
            return new String[] { command, name, major };
        } else {
            return new String[] { command, name, major };
        }

    }

    /**
     * function to validate input
     * 
     * @param arr arguments presented
     * @return retMes status of operations
     */
    private String valInput(String[] arr) {
        String retMes = "";
        if (validateMajor(arr[2])) {
            try {
                if (arr[0].equals("AT") && !validateState(arr[4])) {
                    retMes = "Not part of the tri-state area.";
                } else if (Integer.parseInt(arr[3]) < 0) {
                    retMes = "Credit hours cannot be negative.";
                } else if (Integer.parseInt(arr[3]) < Student.minCredits) {
                    retMes = "Minimum credit hours is 3.";
                } else if (arr[0].equals("AI") && (Integer.parseInt(arr[3]) < International.minCredits)) {
                    retMes = "International students must enroll at least 12 credits.";
                } else if (Integer.parseInt(arr[3]) > Student.maxCredits) {
                    retMes = "Credit hours exceed the maximum 24.";
                } else if (arr[0].contentEquals("AI") && (Integer.parseInt(arr[3]) > 12)
                        && Boolean.parseBoolean(arr[4]) == true) {
                    retMes = "Credit hours exceed the maximum for student in the study abroad program.";
                }
            } catch (NumberFormatException e) {
                return "Invalid credit hours.";
            }
        } else {
            retMes = "'" + arr[2] + "' is not a valid major.";
        }
        return retMes;
    }

    /**
     * function to add Resident student to roster
     * 
     * @param arr arguments to execute command upon
     * @return status of operations
     */
    private String addResident(String[] arr) {
        String retMes = "";
        if (valInput(arr).length() > 0) {
            retMes = valInput(arr);
        } else if (roster.add(new Resident(arr[1], parseMajor(arr[2]), Integer.parseInt(arr[3])))) {
            retMes = "Student added.";
        } else {
            retMes = "Student is already in the roster.";
        }
        return retMes;
    }

    /**
     * function to add Non Resident student to roster
     * 
     * @param arr arguments to execute command upon
     * @return status of operations
     */
    private String addNonResident(String[] arr) {
        String retMes = "";
        if (valInput(arr).length() > 0) {
            retMes = valInput(arr);
        } else if (roster.add(new NonResident(arr[1], parseMajor(arr[2]), Integer.parseInt(arr[3])))) {
            retMes = "Student added.";
        } else {
            retMes = "Student is already in the roster.";
        }
        return retMes;
    }

    /**
     * function to add Tristate student to roster
     * 
     * @param arr arguments to execute command upon
     * @return status of operations
     */
    private String addTristate(String[] arr) {
        String retMes = "";
        if (valInput(arr).length() > 0) {
            retMes = valInput(arr);
        } else if (roster
                .add(new TriState(arr[1], parseMajor(arr[2]), Integer.parseInt(arr[3]), arr[4].toUpperCase()))) {
            retMes = "Student added.";
        } else {
            retMes = "Student is already in the roster.";
        }
        return retMes;
    }

    /**
     * function to add International Student to roster
     * 
     * @param arr arguments to execute command upon
     * @return status of operations
     */
    private String addInternational(String[] arr) {
        String retMes = "";
        if (valInput(arr).length() > 0) {
            retMes = valInput(arr);
        } else if (roster.add(new International(arr[1], parseMajor(arr[2]), Integer.parseInt(arr[3]),
                Boolean.parseBoolean(arr[4])))) {
            retMes = "Student added.";
        } else {
            retMes = "Student is already in the roster.";
        }
        return retMes;
    }

    /**
     * Function to check if provided major is a valid major
     * 
     * @param m; the major as string
     * @return if m is a valid major
     */
    private boolean validateMajor(String m) {
        String[] majorStrings = { "CS", "IT", "BA", "EE", "ME" };
        for (String x : majorStrings) {
            if ((m.toUpperCase()).equals(x)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to parse major from string version of major
     * 
     * @param m; the major as a string
     * @return enum version of major
     */
    public Major parseMajor(String m) {
        return Major.valueOf(m.toUpperCase());
    }

    /**
     * Function to check if provided state is in the tristate area
     * 
     * @param s; the state as string
     * @return if s is a valid state
     */
    private boolean validateState(String s) {
        String[] stateStrings = { "NY", "CT", };
        for (String x : stateStrings) {
            if ((s.toUpperCase()).equals(x)) {
                return true;
            }
        }
        return false;
    }

    /**
     * function to remove Student from roster
     * 
     * @param arr arguments to execute command upon
     * @return status of operations
     */
    private String removeStudent(String[] arr) {
        String retMes = "";
        if (roster.remove(new Student(arr[1], parseMajor(arr[2])))) {
            retMes = "Student removed from the roster.";
        } else {
            retMes = "Student is not in the roster.";
        }
        return retMes;
    }

    /**
     * function to calculate tuition of all students in roster
     * 
     * @return status of operations
     */
    private String calculateTuition() {
        return roster.calcTuition();
    }

    /**
     * function to submit a tuition payment for a specified student
     * 
     * @param arr arguments to execute command upon
     * @return status of operations
     */
    private String payT(String[] arr) {
        String retMes = "";
        Date date;
        Double payment;
        Student student;
        try {
            date = new Date(dpDatePicker.getValue().toString());
            if (!date.isValid()) {
                retMes = "Invalid payment date.";
                return retMes;
            }
        } catch (NullPointerException e) {
            date = new Date();
        }

        payment = Double.parseDouble(arr[3]);

        if (roster.getStudent(new Student(arr[1], parseMajor(arr[2]))) == null) {
            retMes = "Student is not in the roster.";
        } else {
            student = roster.getStudent(new Student(arr[1], parseMajor(arr[2])));
            if (payment <= 0) {
                retMes = "Invalid amount.";
            } else if (payment > student.getTuitionDue()) {
                retMes = "Amount is greater than amount due.";
            } else {
                retMes = "Payment applied.";
                student.setLastPaymentDate(date);
                student.setFullPayment(payment);
                roster.calcSingle(student);
            }
        }
        return retMes;
    }

    /**
     * function to set study abroad status of student to true
     * 
     * @param arr arguments to execute command upon
     * @return status of operations
     */
    private String setSASTrue(String[] arr) {
        String retMes = "";
        retMes = roster.setSAS(new Student(arr[1], parseMajor(arr[2])));
        return retMes + "\n for" + focus.toString();
    }

    /**
     * function to add set the financial aid amount for a resident student
     * 
     * @param arr arguments to execute command upon
     * @return status of operations
     */
    private String setFinAid(String[] arr) {
        String retMes = "";
        int valParam = 4;
        if (arr.length == valParam) {
            Double payment = Double.parseDouble(arr[3]);
            if ((roster.getStudent(new Student(arr[1], parseMajor(arr[2]))) == null)) {
                retMes = "Student not in the roster.";
            } else {
                String[] sArray = ((roster.getStudent(new Student(arr[1], parseMajor(arr[2]))).toString()).split(":"));
                String status = sArray[sArray.length - 1].trim();
                if (status.equals("resident")) {
                    if (!(roster.getStudent(new Student(arr[1], parseMajor(arr[2]))).checkFullTime())) {
                        retMes = "Parttime student doesn't qualify for the award.";
                    } else if (payment < 0
                            || payment > (roster.getStudent(new Student(arr[1], parseMajor(arr[2])))).getTuitionDue()
                            || (payment > Resident.maxFinAid)) {
                        retMes = "Invalid amount.";
                    } else {
                        ((Resident) roster.getStudent(new Student(arr[1], parseMajor(arr[2])))).setFinAid(payment);
                        roster.getStudent(new Student(arr[1], parseMajor(arr[2]))).tuitionDue();
                        roster.calcSingle(focus);
                        retMes = "Tuition updated.";
                    }

                } else {
                    retMes = "Not a resident student.";
                }
            }
        } else if (arr.length == valParam - 1) {
            retMes = "Missing the amount.";
        } else {
            retMes = "Missing data in command line.";
        }
        return retMes;
    }

    /**
     * function to print roster of students
     * 
     * @return status of operations
     */
    private String printRoster() {
        String retMes = "";
        boolean empty = true;
        Student[] rosterB = roster.getRoster();
        for (int y = 0; y < rosterB.length; y++) {
            if (rosterB[y] != null)
                empty = false;
        }
        if (empty) {
            retMes = "Student roster is empty!";
        } else {
            retMes += "* list of students in the roster **\n";
            for (int i = 0; i < rosterB.length; i++) {
                if (rosterB[i] != null) {
                    retMes += rosterB[i].toString() + "\n";
                }
            }
            retMes += "** end of roster **";
        }
        return retMes;
    }

    /**
     * function to print roster of students sorted by student name
     * 
     * @return status of operations
     */
    private String printRosterBN() {
        String retMes = "";
        boolean empty = true;
        Student[] rosterBN = roster.getRosterBN();
        for (int y = 0; y < rosterBN.length; y++) {
            if (rosterBN[y] != null)
                empty = false;
        }
        if (empty) {
            retMes = "Student roster is empty!";
        } else {
            retMes += "* list of students ordered by name **\n";
            for (int x = 0; x < rosterBN.length; x++) {
                if (rosterBN[x] != null)
                    retMes += rosterBN[x].toString() + "\n";
            }
            retMes += "** end of roster **";
        }
        return retMes;
    }

    /**
     * function to print roster of students who have made payments, ordered by the
     * payment date
     * 
     * @return status of operations
     */
    private String printRosterBPD() {
        String retMes = "";
        boolean empty = true;
        Student[] rosterBPD = roster.getRosterBPD();
        for (int y = 0; y < rosterBPD.length; y++) {
            if (rosterBPD[y] != null) {
                empty = false;
            }
        }
        if (empty) {
            retMes = "Student roster is empty!";
        } else {
            retMes += "* list of students made payments ordered by payment date **\n";
            for (int x = 0; x < rosterBPD.length; x++) {
                if (rosterBPD[x] != null && rosterBPD[x].getLastPaymentDate() != null)
                    retMes += rosterBPD[x].toString() + "\n";
            }
            retMes += "** end of roster **";
        }
        return retMes;
    }

    /**
     * enables the nonResident toggle group
     * 
     * @param Event - on rdbNonRes selected
     */
    @FXML
    private void AddNonResOptions(ActionEvent Event) {
        rdbNY.setDisable(false);
        rdbCT.setDisable(false);
        rdbInt.setDisable(false);
    }

    /**
     * disables the nonResident toggle group
     * 
     * @param Event - on rdbRes selected
     */
    @FXML
    private void RemoveNonResOptions(ActionEvent Event) {
        rdbNY.setDisable(true);
        rdbCT.setDisable(true);
        rdbInt.setDisable(true);
        tgSAS.setDisable(true);
        tgSAS.setSelected(false);
        rdbInt.setSelected(true);
        rdbInt.setSelected(false);
    }

    /**
     * Disables togglebutton to set SAS status is student is not international
     * 
     * @param event Event - on rdbNY or rdbCT selected
     */
    @FXML
    private void DisablesetSAS(ActionEvent event) {
        tgSAS.setDisable(true);
        tgSAS.setSelected(false);
    }

    /**
     * Enables togglebutton to set SAS status is student is international
     * 
     * @param event Event - on rdbInt selected
     */
    @FXML
    private void EnablesetSAS(ActionEvent event) {
        tgSAS.setDisable(false);
        tgSAS.setSelected(false);
    }

    /**
     * Adds or Removes the change SAS status options from the pay tuition tab
     * 
     * @param status true or false depending on whether or not to hide change SAS
     *               options
     */
    private void addChangeSASOptions(boolean status) {
        if (focus != null) {
            if (focus instanceof International) {
                if (((International) focus).getStudyAbroad()) {
                    btnchangeSAS.setSelected(true);
                } else {
                    btnchangeSAS.setSelected(false);
                }
            }
        }
        lblStatus.setVisible(status);
        btnchangeSAS.setVisible(status);
        btnchangeSAS.setDisable(!status);
    }

    /**
     * Adds or Removes the pay financial aid options from the pay tuition tab
     * 
     * @param status true or false depending on whether or not to hide pay financial
     *               aid options
     */
    private void addPayFinAidOptions(boolean status) {
        lblFinAidEligible.setVisible(status);
        lblFinds.setVisible(status);
        txtFinAid.setVisible(status);
        btnPayFinAid.setVisible(status);
    }

    /**
     * Gets the major selected from the Majors toggleGroup
     * 
     * @return String major of selected togglebutton
     */
    private String getMajor() {
        if (rdbCS.isSelected()) {
            return "CS";
        } else if (rdbIT.isSelected()) {
            return "IT";
        } else if (rdbEE.isSelected()) {
            return "EE";
        } else if (rdbME.isSelected()) {
            return "ME";
        } else {
            return "BA";
        }
    }

    /**
     * Gets the major selected from the MajorPayments toggleGroup
     * 
     * @return String major of selected togglebutton on pay tuition tab
     */
    private String getMajorPayments() {
        if (rdbCSP.isSelected()) {
            return "CS";
        } else if (rdbITP.isSelected()) {
            return "IT";
        } else if (rdbEEP.isSelected()) {
            return "EE";
        } else if (rdbMEP.isSelected()) {
            return "ME";
        } else {
            return "BA";
        }
    }
}
