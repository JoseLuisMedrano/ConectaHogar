// Esperar a que el DOM (la estructura de la página) esté completamente cargado
document.addEventListener('DOMContentLoaded', function () {
    'use strict';

    // ===================================================================
    // LÓGICA COMPARTIDA (para Login y Registro)
    // Oculta las alertas de éxito/error después de 5 segundos
    // ===================================================================
    const alerts = document.querySelectorAll('.alert');
    if (alerts.length > 0) {
        setTimeout(() => {
            alerts.forEach(alert => {
                // Inicia una transición suave para desvanecer la alerta
                alert.style.transition = 'opacity 0.5s ease';
                alert.style.opacity = '0';
                // Elimina el elemento del DOM después de que termine la transición
                setTimeout(() => alert.remove(), 500);
            });
        }, 5000); // 5 segundos
    }


    // ===================================================================
    // LÓGICA EXCLUSIVA PARA LA PÁGINA DE REGISTRO
    // (Se ejecuta solo si encuentra el formulario de registro)
    // ===================================================================
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        // --- Selección de elementos del formulario ---
        const password = document.getElementById('contrasena');
        const confirmPassword = document.getElementById('confirmarContrasena');
        const tipoUsuarioRadios = document.querySelectorAll('input[name="tipoUsuario"]');
        const camposTecnicoDiv = document.getElementById('camposTecnico');
        const especialidadSelect = document.getElementById('especialidad');
        const submitButton = document.getElementById('submitButton');
        const buttonText = document.getElementById('buttonText');
        const buttonSpinner = document.getElementById('buttonSpinner');

        // --- Lógica para mostrar/ocultar campos de Técnico ---
        const toggleTecnicoFields = () => {
            // Busca si el radio "TECNICO" está seleccionado
            const tecnicoSeleccionado = document.querySelector('input[name="tipoUsuario"]:checked');
            
            if (tecnicoSeleccionado && tecnicoSeleccionado.value === 'TECNICO') {
                camposTecnicoDiv.style.display = 'flex'; // Muestra la sección
                especialidadSelect.required = true;     // Hace el campo de especialidad obligatorio
            } else {
                camposTecnicoDiv.style.display = 'none'; // Oculta la sección
                especialidadSelect.required = false;    // Quita el requerimiento
            }
        };

        // Añadir un "escuchador" a los botones de radio de tipo de usuario
        tipoUsuarioRadios.forEach(radio => {
            radio.addEventListener('change', toggleTecnicoFields);
        });
        
        // Ejecutar una vez al cargar por si el formulario recuerda una selección previa
        toggleTecnicoFields();

        // --- Lógica para validación de contraseñas ---
        const validatePasswords = () => {
            if (password.value !== confirmPassword.value) {
                // Usa la validación de Bootstrap para mostrar un mensaje de error
                confirmPassword.setCustomValidity("Las contraseñas no coinciden.");
                confirmPassword.classList.add('is-invalid');
            } else {
                confirmPassword.setCustomValidity("");
                confirmPassword.classList.remove('is-invalid');
            }
        };
        
        if (password && confirmPassword) {
            password.addEventListener('input', validatePasswords);
            confirmPassword.addEventListener('input', validatePasswords);
        }

        // --- Lógica para el envío del formulario ---
        registerForm.addEventListener('submit', function (event) {
            validatePasswords();
            
            if (!registerForm.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            } else {
                // Si es válido, mostrar spinner de carga
                if (submitButton && buttonText && buttonSpinner) {
                    buttonText.classList.add('d-none');
                    buttonSpinner.classList.remove('d-none');
                    submitButton.disabled = true;
                }
            }
            registerForm.classList.add('was-validated');
        }, false);
    }
});