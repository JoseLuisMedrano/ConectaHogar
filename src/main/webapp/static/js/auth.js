// Usamos este evento para asegurarnos de que el HTML esté completamente cargado antes de ejecutar el script.
document.addEventListener('DOMContentLoaded', function () {
    'use strict';

    // --- Lógica para la página de REGISTRO ---
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        const password = document.getElementById('contrasena');
        const confirmPassword = document.getElementById('confirmarContrasena');
        const submitButton = document.getElementById('submitButton');
        const buttonText = document.getElementById('buttonText');
        const buttonSpinner = document.getElementById('buttonSpinner');

        // Función para validar que las contraseñas coincidan
        const validatePasswords = () => {
            if (password.value !== confirmPassword.value) {
                confirmPassword.classList.add('is-invalid'); // Muestra el mensaje de error de Bootstrap
            } else {
                confirmPassword.classList.remove('is-invalid');
            }
        };

        // Añadir los "escuchadores" de eventos a los campos de contraseña
        if(password && confirmPassword) {
            password.addEventListener('input', validatePasswords);
            confirmPassword.addEventListener('input', validatePasswords);
        }

        // Lógica para el envío del formulario
        registerForm.addEventListener('submit', function (event) {
            validatePasswords();

            // Si el formulario no es válido o las contraseñas no coinciden, detener el envío
            if (!registerForm.checkValidity() || (password && confirmPassword && password.value !== confirmPassword.value)) {
                event.preventDefault();
                event.stopPropagation();
            } else {
                // Si todo es válido, mostrar el spinner de carga
                if(submitButton && buttonText && buttonSpinner) {
                    buttonText.classList.add('d-none');
                    buttonSpinner.classList.remove('d-none');
                    submitButton.disabled = true;
                }
            }
            registerForm.classList.add('was-validated');
        }, false);
    }

    // --- Lógica compartida para LOGIN y REGISTRO ---
    // Ocultar las alertas de éxito/error después de 5 segundos
    const alerts = document.querySelectorAll('.alert');
    if (alerts.length > 0) {
        setTimeout(() => {
            alerts.forEach(alert => {
                alert.style.transition = 'opacity 0.5s ease';
                alert.style.opacity = '0';
                setTimeout(() => alert.remove(), 500); // Elimina el elemento del DOM después de la transición
            });
        }, 5000);
    }
});