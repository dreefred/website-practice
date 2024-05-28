<!DOCTYPE html>
<html data-bs-theme="light" lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>PDF Options</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Inter:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&amp;display=swap">
    <link rel="stylesheet" href="assets/css/Slider-Range.css">
</head>

<body>
     <%
            if(session.getAttribute("auth") == null) {
                response.sendRedirect("invalidAccess.jsp");
            }
            session.setAttribute("CaptchaString", null);
            
            response.setHeader("Cache-Control", "no-cache, no-store");
            response.setDateHeader("Expires", 0);
            
            String user = session.getAttribute("role").toString();
        %>
    <nav class="navbar navbar-expand-md sticky-top py-3 navbar-dark" id="mainNav">
        <div class="container"><a class="navbar-brand d-flex align-items-center" href="/"><span class="bs-icon-sm bs-icon-circle bs-icon-primary shadow d-flex justify-content-center align-items-center me-2 bs-icon"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-bezier">
                        <path fill-rule="evenodd" d="M0 10.5A1.5 1.5 0 0 1 1.5 9h1A1.5 1.5 0 0 1 4 10.5v1A1.5 1.5 0 0 1 2.5 13h-1A1.5 1.5 0 0 1 0 11.5zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm10.5.5A1.5 1.5 0 0 1 13.5 9h1a1.5 1.5 0 0 1 1.5 1.5v1a1.5 1.5 0 0 1-1.5 1.5h-1a1.5 1.5 0 0 1-1.5-1.5zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zM6 4.5A1.5 1.5 0 0 1 7.5 3h1A1.5 1.5 0 0 1 10 4.5v1A1.5 1.5 0 0 1 8.5 7h-1A1.5 1.5 0 0 1 6 5.5zM7.5 4a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5z"></path>
                        <path d="M6 4.5H1.866a1 1 0 1 0 0 1h2.668A6.517 6.517 0 0 0 1.814 9H2.5c.123 0 .244.015.358.043a5.517 5.517 0 0 1 3.185-3.185A1.503 1.503 0 0 1 6 5.5zm3.957 1.358A1.5 1.5 0 0 0 10 5.5v-1h4.134a1 1 0 1 1 0 1h-2.668a6.517 6.517 0 0 1 2.72 3.5H13.5c-.123 0-.243.015-.358.043a5.517 5.517 0 0 0-3.185-3.185z"></path>
                    </svg></span><span>Group 2</span></a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="navbar-nav mx-auto">
                    <li class="nav-item"><a class="nav-link" href="success2.jsp">Home</a></li>
                </ul><a class="btn btn-primary shadow" role="button" href="index.jsp">Logout</a>
            </div>
        </div>
    </nav>
    <section>
        <form action="PDFSettingServlet" method="POST">
            <div class="container py-5" style="min-width: 0;">
            <div class="row mb-4 mb-lg-5">
                <div class="col-md-8 col-xl-6 text-center mx-auto">
                    <p class="fw-bold text-success mb-2">Report</p>
                    <h2 class="fw-bold">Generate PDF Options</h2>
                </div>
            </div>
            <div class="col">
                <%if(!user.equals("student")){%>
                <div style="border-radius: 99px 0px 0px;border-top-left-radius: 87px;">
                    <ul class="nav nav-tabs" role="tablist">
                        <%if(user.equals("admin")){%>
                        <li class="nav-item" role="presentation"><a class="nav-link <%if(user.equals("admin")){%>active <%}%>" role="tab" data-bs-toggle="tab" href="#tab-1"><span style="color: rgb(255, 255, 255);">Specify Date</span></a></li>
                        <%}if(user.equals("instructor")){%>
                        <li class="nav-item" role="presentation"><a class="nav-link <%if(user.equals("instructor")){%>active <%}%>" role="tab" data-bs-toggle="tab" href="#tab-2"><span style="color: rgb(253, 255, 255);">Specify Course</span></a></li>
                        <%}if(user.equals("admin")){%>
                        <li class="nav-item" role="presentation"><a class="nav-link" role="tab" data-bs-toggle="tab" href="#tab-3"><span style="color: rgb(255, 255, 255);">Specify Role</span></a></li>
                        <%}%>
                    </ul>
                    <div class="tab-content" style="border-radius: 0px;border-bottom-left-radius: 14px;border-bottom-right-radius: 14px;border-color: rgb(204,204,204);border-right: 1.5px solid rgb(230,223,223) ;border-bottom-style: double;border-left: 1.5px solid rgb(208,212,217) ;">
                        <%if(user.equals("admin")){%>
                        <div class="tab-pane <%if(user.equals("admin")){%>active <%}%>" role="tabpanel" id="tab-1">
                            <h1 style="color: var(--bs-form-valid-color);padding-left: 0px;"><strong>&nbsp;Date Options</strong></h1>
                            <p style="padding-left: 10px;">Specify the range of the date of the report that you want to generate</p>
                            <div class="col">
                                <label class="form-label" style="padding-left: 10px;">Enter Start Date:</label><input class="form-control" type="date" name="dateStart" id="dateStart" style="width: 360px;margin: 11px;"><label class="form-label" style="padding-left: 10px;padding-top: 12px;">Enter End Date:</label><input class="form-control" name="dateEnd" type="date" id="dateEnd"  style="width: 360px;margin: 11px;">
                            </div>
                        </div>
                        <%}if(user.equals("instructor")|| user.equals("student")){%>
                        <div class="tab-pane <%if(user.equals("instructor")){%>active <%}%>" role="tabpanel" id="tab-2">
                            <h1 style="color: var(--bs-form-valid-color);"><strong>&nbsp;Course Options</strong></h1>
                            <p style="padding-left: 10px;">Specify the course that you want to include</p>
                            <div class="col">
                                <label class="form-label" style="padding-left: 10px;">Courses available:</label>
                                    <div class="form-check" style="padding-left: 40px;"><input name="c1" class="form-check-input" type="checkbox" id="formCheck-3"><label class="form-check-label" for="formCheck-3"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">ITIL� 4 Foundation Certification Program</span></label></div>
                                    <div class="form-check" style="padding-left: 40px;"><input name="c2" class="form-check-input" type="checkbox" id="formCheck-1"><label class="form-check-label" for="formCheck-1"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">CompTIA Security+</span></label></div>
                                    <div class="form-check" style="padding-left: 40px;"><input name="c3" class="form-check-input" type="checkbox" id="formCheck-2"><label class="form-check-label" for="formCheck-2"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">User Experience (UX)</span></label></div>
                                    <div class="form-check" style="padding-left: 40px;"><input name="c4" class="form-check-input" type="checkbox" id="formCheck-4"><label class="form-check-label" for="formCheck-4"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">EC-Council Certified Ethical Hacker v12 (CEH)</span></label></div>
                                    <div class="form-check" style="padding-left: 40px;"><input name="c5" class="form-check-input" type="checkbox" id="formCheck-5"><label class="form-check-label" for="formCheck-5"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">Microsoft Excel Essentials</span></label></div>
                                    <div class="form-check" style="padding-left: 40px;"><input name="c6" class="form-check-input" type="checkbox" id="formCheck-6"><label class="form-check-label" for="formCheck-6"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">Agile Project Management with Scrum</span></label></div>
                            </div>
                        </div>
                        <%}if(user.equals("admin")){%>
                        <div class="tab-pane" role="tabpanel" id="tab-3">
                            <h1 style="color: var(--bs-form-valid-color);"><strong>&nbsp;Role Options</strong></h1>
                            <p style="padding: 0px;padding-right: 0px;padding-left: 10px;">Generate a masterlist given the following roles</p>
                            <div class="col">
                                <label class="form-label" style="padding-left: 10px;">Roles available:</label>
                                    <div class="form-check" style="padding-left: 40px;"><input name="role1" class="form-check-input" type="checkbox" id="formCheck-11"><label class="form-check-label" for="formCheck-11"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">Admin</span></label></div>
                                    <div class="form-check" style="padding-left: 40px;"><input name="role2" class="form-check-input" type="checkbox" id="formCheck-12"><label class="form-check-label" for="formCheck-12"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">Instructor</span></label></div>
                                    <div class="form-check" style="padding-left: 40px;"><input name="role3" class="form-check-input" type="checkbox" id="formCheck-13"><label class="form-check-label" for="formCheck-13"><span style="color: rgb(236, 236, 236); background-color: rgb(33, 33, 33);">Student</span></label></div>                                   
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="row d-flex justify-content-center">
                <div class="col-md-6 col-xl-4">
                    <div class="card"></div>
                </div>
            </div>
        </div>
        <div class="container">
            
            <ul class="list-group">
                <%if(user.equals("admin")){%>
                <li class="list-group-item">
                    <div class="form-check"><input class="form-check-input" type="checkbox" id="formCheck-7" name="dateEnabled" onclick="return checkDate()"><label class="form-check-label" for="formCheck-7" >Enable Date Inquiry</label></div>
                </li>
                <%}if(user.equals("instructor")){%>
                <li class="list-group-item">
                    <div class="form-check"><input class="form-check-input" type="checkbox" id="formCheck-8" name="courseEnabled" onclick="return checkCourses()"><label class="form-check-label" for="formCheck-8">Enable Course Inquiry</label></div>
                </li>
                <%}if(user.equals("admin")){%>
                <li class="list-group-item">
                    <div class="form-check"><input class="form-check-input" type="checkbox" id="formCheck-9" name="roleEnabled" onclick="return checkRole()"><label class="form-check-label" for="formCheck-9" >Enable Role Inquiry</label></div>
                </li>
                <%}%>
            </ul>
            <div class=" text-center mx-auto">
                <%if(!user.equals("student")){%>
                <p class="fw"><br><b>Note</b>: Please select the checkbox for the filters to take effect. Not selecting anything will generate a default report.</p>
                <%}else{%>
                <p class="fw"><br>Your PDF report has been prepared. Please click on generate to display your report.</p>
                <%}%>
                
            </div>
        </div>
            
        <div class="container">
            <div class="row">
                <div class="col-md-12" style="height: 34px;">
                    <div class="w-100" style="width: 911px;min-width: 0px;"></div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="w-100"></div>
                </div>
            </div>
        </div>
        <div class="container text-center"><button class="btn btn-primary" type="submit" name="genReport" id="genRep" >Generate PDF</button>
            <div class="row">
                <div class="w-100" style="height: 21px;"></div>
            </div>
        </div>
        </form>
      
    </section>
    <footer class="bg-dark">
        <div class="container py-4 py-lg-5">
            <div class="row justify-content-center">
                <div class="col-sm-4 col-md-3 text-center text-lg-start d-flex flex-column">
                    <h3 class="fs-6 fw-bold">Services</h3>
                    <ul class="list-unstyled">
                        <li><a href="#">Web design</a></li>
                        <li><a href="#">Development</a></li>
                        <li><a href="#">Hosting</a></li>
                    </ul>
                </div>
                <div class="col-sm-4 col-md-3 text-center text-lg-start d-flex flex-column">
                    <h3 class="fs-6 fw-bold">About</h3>
                    <ul class="list-unstyled">
                        <li><a href="#">Company</a></li>
                        <li><a href="#">Team</a></li>
                        <li><a href="#">Legacy</a></li>
                    </ul>
                </div>
                <div class="col-sm-4 col-md-3 text-center text-lg-start d-flex flex-column">
                    <h3 class="fs-6 fw-bold">Careers</h3>
                    <ul class="list-unstyled">
                        <li><a href="#">Job openings</a></li>
                        <li><a href="#">Employee success</a></li>
                        <li><a href="#">Benefits</a></li>
                    </ul>
                </div>
                <div class="col-lg-3 text-center text-lg-start d-flex flex-column align-items-center order-first align-items-lg-start order-lg-last">
                    <div class="fw-bold d-flex align-items-center mb-2"><span class="bs-icon-sm bs-icon-circle bs-icon-primary d-flex justify-content-center align-items-center bs-icon me-2"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-bezier">
                                <path fill-rule="evenodd" d="M0 10.5A1.5 1.5 0 0 1 1.5 9h1A1.5 1.5 0 0 1 4 10.5v1A1.5 1.5 0 0 1 2.5 13h-1A1.5 1.5 0 0 1 0 11.5zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm10.5.5A1.5 1.5 0 0 1 13.5 9h1a1.5 1.5 0 0 1 1.5 1.5v1a1.5 1.5 0 0 1-1.5 1.5h-1a1.5 1.5 0 0 1-1.5-1.5zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zM6 4.5A1.5 1.5 0 0 1 7.5 3h1A1.5 1.5 0 0 1 10 4.5v1A1.5 1.5 0 0 1 8.5 7h-1A1.5 1.5 0 0 1 6 5.5zM7.5 4a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5z"></path>
                                <path d="M6 4.5H1.866a1 1 0 1 0 0 1h2.668A6.517 6.517 0 0 0 1.814 9H2.5c.123 0 .244.015.358.043a5.517 5.517 0 0 1 3.185-3.185A1.503 1.503 0 0 1 6 5.5zm3.957 1.358A1.5 1.5 0 0 0 10 5.5v-1h4.134a1 1 0 1 1 0 1h-2.668a6.517 6.517 0 0 1 2.72 3.5H13.5c-.123 0-.243.015-.358.043a5.517 5.517 0 0 0-3.185-3.185z"></path>
                            </svg></span><span>Brand</span></div>
                    <p class="text-muted"><%=getServletContext().getInitParameter("footer")%></p>
                </div>
            </div>
            <hr>
            <div class="text-muted d-flex justify-content-between align-items-center pt-3">
                <p class="mb-0"><%=getServletContext().getInitParameter("copyright")%></p>
                <ul class="list-inline mb-0">
                    <li class="list-inline-item"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-facebook">
                            <path d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951"></path>
                        </svg></li>
                    <li class="list-inline-item"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-twitter">
                            <path d="M5.026 15c6.038 0 9.341-5.003 9.341-9.334 0-.14 0-.282-.006-.422A6.685 6.685 0 0 0 16 3.542a6.658 6.658 0 0 1-1.889.518 3.301 3.301 0 0 0 1.447-1.817 6.533 6.533 0 0 1-2.087.793A3.286 3.286 0 0 0 7.875 6.03a9.325 9.325 0 0 1-6.767-3.429 3.289 3.289 0 0 0 1.018 4.382A3.323 3.323 0 0 1 .64 6.575v.045a3.288 3.288 0 0 0 2.632 3.218 3.203 3.203 0 0 1-.865.115 3.23 3.23 0 0 1-.614-.057 3.283 3.283 0 0 0 3.067 2.277A6.588 6.588 0 0 1 .78 13.58a6.32 6.32 0 0 1-.78-.045A9.344 9.344 0 0 0 5.026 15"></path>
                        </svg></li>
                    <li class="list-inline-item"><svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16" class="bi bi-instagram">
                            <path d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.917 3.917 0 0 0-1.417.923A3.927 3.927 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.916 3.916 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.926 3.926 0 0 0-.923-1.417A3.911 3.911 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0h.003zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599.28.28.453.546.598.92.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.47 2.47 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.478 2.478 0 0 1-.92-.598 2.48 2.48 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233 0-2.136.008-2.388.046-3.231.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92.28-.28.546-.453.92-.598.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045v.002zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92zm-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217zm0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334"></path>
                        </svg></li>
                </ul>
            </div>
        </div>
    </footer>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/bs-init.js"></script>
    <script src="assets/js/bold-and-dark.js"></script>
    <script src="assets/js/pdfSetting.js"></script>
    <script>
        window.onload = function() {
            initializeListeners();
        };
    </script>
</body>

</html>