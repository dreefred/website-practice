// Function to initialize event listeners
function initializeListeners() {
    // Add event listener to date input fields
    document.getElementById("dateStart").addEventListener("change", function() {
        // Check dates if the date inquiry checkbox is enabled
        if (document.getElementById("formCheck-7").checked) {
            checkDate();
        }
    });

    document.getElementById("dateEnd").addEventListener("change", function() {
        // Check dates if the date inquiry checkbox is enabled
        if (document.getElementById("formCheck-7").checked) {
            checkDate();
        }
    });

    // Add event listener to date inquiry checkbox
    document.getElementById("formCheck-7").addEventListener("change", function() {
        // Check dates if the date inquiry checkbox is enabled
        if (this.checked) {
            checkDate();
        }
    });
}

// Call the initializeListeners function to set up event listeners
initializeListeners();

// Function to validate date
function checkDate() {
    var dateEnabled = document.getElementById("formCheck-7").checked;
    if (dateEnabled) {
        var dateStart = document.getElementById("dateStart").value;
        var dateEnd = document.getElementById("dateEnd").value;

        // Check if both dates are provided
        if (dateStart.trim() === "" || dateEnd.trim() === "") {
            alert("Please provide both start and end dates.");
            return false;
        }

        // Convert date strings to Date objects for comparison
        var startDate = new Date(dateStart);
        var endDate = new Date(dateEnd);

        // Check if end date is less than start date
        if (endDate < startDate) {
            alert("End date cannot be less than start date.");
            return false;
        }
    }
    return true;
}

function checkCourses() {
        var courseEnabled = document.getElementById("formCheck-8").checked;
        if (courseEnabled) {
            var atLeastOneChecked = false;
            for (var i = 1; i <= 6; i++) {
                var checkbox = document.getElementById("formCheck-" + i);
                if (checkbox.checked) {
                    atLeastOneChecked = true;
                    break;
                }
            }

            if (!atLeastOneChecked) {
                alert("Please select at least one course.");
                return false;
            }
        }
        return true;
    }
    
    function checkRole() {
    var roleEnabled = document.getElementById("formCheck-9").checked;
    
    if (roleEnabled) {
        var atLeastOneChecked = false;
        for (var i = 11; i <= 13; i++) { // Changed loop start index to 11
            var checkbox = document.getElementById("formCheck-" + i);
            if (checkbox.checked) {
                atLeastOneChecked = true;
                break;
            }
        }

        if (!atLeastOneChecked) {
            alert("Please select at least one role.");
            return false;
        }
    }
    return true;
}


// Function to validate form before generating report
function validateForm() {
    // Check if course inquiry is enabled
    var courseEnabled = document.getElementById("formCheck-8").checked;
    if (courseEnabled && !checkCourses()) {
        return false; // Return false if course validation fails
    }

    // Check if date inquiry is enabled
    var dateEnabled = document.getElementById("formCheck-7").checked;
    if (dateEnabled && !checkDate()) {
        return false; // Return false if date validation fails
    }

    // Check if user inquiry is enabled
    var roleEnabled = document.getElementById("formCheck-9").checked;
    if (roleEnabled && !checkRole()) {
        return false; // Return false if user validation fails
    }

    // If all validations pass, return true
    return true;
}


document.getElementById("genRep").addEventListener("click", function(event) {
    // Call the validateForm function
    if (!validateForm()) {
        // Prevent default form submission behavior if validation fails
        event.preventDefault();
    }
});