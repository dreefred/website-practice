<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-bs-theme="light" lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>Sign Up</title>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/Inter.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/3.5.2/animate.min.css">
        <script>
            document.addEventListener('selectstart', function(e) {
                e.preventDefault();
                return false;
            });

            document.addEventListener('contextmenu', function(e) {
                e.preventDefault();
                return false;
            });
        </script>
    </head>
    <body>
        <div class="row mb-4 mb-lg-5">
            <div class="col-md-8 col-xl-6 text-center mx-auto">
                <p class="fw-bold text-success mb-2">Sign Up</p>
                <h2 class="fw-bold">Sign up</h2>
            </div>
            <div class="row d-flex justify-content-center">
                <div class="col-md-6 col-xl-4">
                    <div class="card">
                        <div class="card-body text-center d-flex flex-column align-items-center">
                            <div class="bs-icon-xl bs-icon-circle bs-icon-primary shadow bs-icon my-4" data-bss-hover-animate="bounce">
                                <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-person">
                                    <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0m4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4m-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664z"></path>
                                </svg>
                            </div>
                            <form action="SignupServlet" method="post">
                                <div class="mb-3"><input class="form-control" type="email" name="uname" placeholder="Email" required></div>
                                <div class="mb-3"><input class="form-control" type="password" name="upass" placeholder="Password" required></div>
                                <div class="mb-3"><input class="form-control" type="password" name="confirm-upass" placeholder="Confirm Password" required></div>
                                <div class="mb-3">
                                    <label class="form-check-label" for="student">Student</label>
                                    <input class="form-check-input" type="radio" name="role" id="student" value="student" checked>
                                    <label class="form-check-label" for="instructor">Instructor</label>
                                    <input class="form-check-input" type="radio" name="role" id="instructor" value="instructor">
                                </div>
                                <div class="mb-3"><button class="btn btn-primary shadow d-block w-100" data-bss-hover-animate="jello" type="submit" value="Sign up">Sign up</button></div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>