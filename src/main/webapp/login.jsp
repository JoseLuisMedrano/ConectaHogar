<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Iniciar sesión</title>
        <link
            href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css"rel="stylesheet"/>
        <link rel="stylesheet" href="css/styles-login.css"/>
    </head>
    <body>
        <div class="container">
            <div class="form-box login">
                <form action="">
                    <h1>Iniciar Sesión</h1>
                    <div class="input-box">
                        <input
                            type="text"
                            placeholder="Nº de dni"
                            autocomplete="new-text"
                            required
                            />
                        <i class="bx bxs-user"></i>
                    </div>
                    <div class="input-box">
                        <input
                            type="password"
                            placeholder="Contraseña"
                            autocomplete="new-email"
                            required
                            />
                        <i class="bx bxs-lock-alt"></i>
                    </div>
                    <div class="forgot-link">
                        <a href="recuperarc.jsp">Olvidaste la contraseña?</a>
                    </div>
                    <button type="submit" class="btn">Ingresar</button>
                    <p>Tienes problemas para iniciar sesión? Comunicate con nosotros</p>
                    <div class="social-icons">
                        <a href="https://www.facebook.com/eltemplogymdp" target="_blank"
                           ><i class="bx bxl-facebook"></i
                            ></a>
                        <a
                            href="https://api.whatsapp.com/send/?phone=%2B51944060192&text=Hola,+deseo+ver+las+promociones&type=phone_number&app_absent=0&fbclid=IwY2xjawKLKYNleHRuA2FlbQIxMABicmlkETE3b0FaQWtSM2VvOVdOanZsAR4xJ8a9akCe779tyC5NyFIaAGiRc7Gfsn8OWLEBvpErvCWLJLrpJrhZcWSq7A_aem_Bp5E2hZFHAhMYZNz58DRhw"
                            target="_blank"
                            ><i class="bx bxl-whatsapp"></i
                            ></a>
                    </div>
                </form>
            </div>

            <div class="form-box register">
                <form action="">
                    <h1>Registro</h1>
                    <div class="input-box">
                        <input type="text" placeholder="Nº de dni" required />
                        <i class="bx bxs-user"></i>
                    </div>
                    <div class="input-box">
                        <input type="email" placeholder="Correo Electronico "autocomplete="new-email"required/>
                        <i class="bx bx-envelope"></i>
                    </div>
                    <div class="input-box">
                        <input type="password" placeholder="Contraseña" autocomplete="new-password" required/>
                        <i class="bx bxs-lock-alt"></i>
                    </div>
                    <div class="forgot-link">
                        <input type="checkbox" id="terminos" name="terminos" value="aceptado"/>
                        <label for="terminos">Aceptar<strong><a class="hero_cta">términos y condiciones
                                </a></strong>
                        </label>
                    </div>

                    <button type="submit" class="btn">Registrarse</button>
                    <p>
                        ¿Tienes problemas para registrarte?<br />
                        Comunicate con nosotros
                    </p>
                    <div class="social-icons">
                        <a href="https://www.facebook.com/eltemplogymdp" target="_blank"><i class="bx bxl-facebook"></i></a>
                        <a href="https://api.whatsapp.com/send/?phone=%2B51944060192&text=Hola,+deseo+ver+las+promociones&type=phone_number&app_absent=0&fbclid=IwY2xjawKLKYNleHRuA2FlbQIxMABicmlkETE3b0FaQWtSM2VvOVdOanZsAR4xJ8a9akCe779tyC5NyFIaAGiRc7Gfsn8OWLEBvpErvCWLJLrpJrhZcWSq7A_aem_Bp5E2hZFHAhMYZNz58DRhw"
                           target="_blank"><i class="bx bxl-whatsapp"></i></a>
                    </div>
                </form>
            </div>

            <div class="toggle-box">
                <div class="toggle-panel toggle-left">
                    <h1>Bienvenido de nuevo</h1>
                    <p>
                        ¿Aun no tienes una cuenta?<br />
                        Crea tu cuenta y empieza tu transformación
                    </p>
                    <button class="btn register-btn">Registrarse</button>
                </div>
                <div class="toggle-panel toggle-right">
                    <h1>Bienvenido</h1>
                    <p>¿Ya tienes una cuenta?</p>
                    <button class="btn login-btn">Iniciar Sesión</button>
                </div>
            </div>
        </div>
        <header class=""></header>
        <section class="modal">
            <div class="modal_container">
                <img src="imagenes/modal.png" class="modal_img" />
                <h2 class="modal_title">Términos y Condiciones</h2>
                <p class="modal_paragraph">
                    Los términos y condiciones del gimnasio establecen que la membresía es
                    personal e intransferible, se debe presentar una identificación
                    válida, y se espera un comportamiento respetuoso en las instalaciones.
                    El uso del equipo debe ser adecuado, y el gimnasio no se hace
                    responsable de lesiones por uso indebido. Las cancelaciones requieren
                    aviso previo, y no se ofrecen reembolsos. Los miembros deben estar en
                    condiciones físicas adecuadas y cualquier condición médica debe ser
                    comunicada. Se pueden aplicar tarifas adicionales para clases
                    especiales, y el gimnasio se reserva el derecho de modificar estos
                    términos en cualquier momento. Al registrarse, los miembros aceptan
                    cumplir con estas condiciones.
                </p>
                <a href="#" class="modal_close">Acepto</a>
            </div>
        </section>
        <script src="js/script.js"></script>
    </body>
</html>