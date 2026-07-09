package practice.main_module;

/**
 * Flexible Constructor bodies
 * ---
 * Can now:
 * - Validate input early
 * - Log information
 * - Initialize local or temporary variable
 * - Early calculations / setup
 *
 * Notes:
 * - Before calling this() or super(), you can write any side-effect-free statements
 * - Can't do anything to the object itself. No use of instance fields, instance methods, "this", or "super" keywords
 */

class EmployeeLogin {
    private String empId;
    private String username;
    private String password;
    private boolean active;
    private boolean showPasswordReset;

    public EmployeeLogin(String empId, String username) {
        if (empId == null || username.isBlank()) {
            throw new IllegalArgumentException("Invalid username");
        }
        this (empId, username, "password", true, true);
    }

    public EmployeeLogin(String empId, String username, String password, boolean active, boolean showPasswordReset) {
        this.empId = empId;
        this.username = username;
        this.active = active;
        this.showPasswordReset = showPasswordReset;
    }

}

public class FlexibleConstructorBodies {
    static void main() {
        EmployeeLogin emp = new EmployeeLogin("empId", "username");
    }

}
