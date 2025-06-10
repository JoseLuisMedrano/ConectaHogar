

// Mostrar/ocultar modal de términos
document.querySelectorAll('.modal-trigger').forEach(item => {
    item.addEventListener('click', function(e) {
        e.preventDefault();
        document.getElementById('termsModal').style.display = 'block';
    });
});

// Cerrar modal
document.querySelector('.modal-close').addEventListener('click', function() {
    document.getElementById('termsModal').style.display = 'none';
    document.getElementById('terminos').checked = true;
});

// Validación básica del formulario de registro
document.getElementById('registerForm').addEventListener('submit', function(e) {
    if (!document.getElementById('terminos').checked) {
        e.preventDefault();
        alert('Debes aceptar los términos y condiciones');
    }
});