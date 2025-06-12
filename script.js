const container = document.querySelector('.container');
const registerBtn = document.querySelector('.register-btn');
const loginBtn = document.querySelector('.login-btn');
const registerForm = document.getElementById('registerForm');
const loginForm = document.getElementById('loginForm');

// Toggle entre login y registro
registerBtn.addEventListener('click', () => {
    container.classList.add('active');
});

loginBtn.addEventListener('click', () => {
    container.classList.remove('active');
});

// Para el modal
const openModal = document.querySelector('.hero_cta');
const modal = document.querySelector('.modal');
const closeModal = document.querySelector('.modal_close');

openModal.addEventListener('click', (e) => {
    e.preventDefault();
    modal.classList.add('modal--show');
});

closeModal.addEventListener('click', (e) => {
    e.preventDefault();
    modal.classList.remove('modal--show');
});

// Validación del formulario de login
loginForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;
    
    if (!email || !password) {
        alert('Por favor ingrese su correo electrónico y contraseña');
        return;
    }
    
    // Redirección después de validar
window.location.href = '/index.html';
});

// Validación del formulario de registro
registerForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Validar teléfono peruano
    const telefono = document.getElementById('telefono').value;
    if (!/^\+51\d{9}$/.test(telefono)) {
        alert('Por favor ingrese un número de teléfono peruano válido (+51 seguido de 9 dígitos)');
        return;
    }
    
    // Validar edad
    const edad = document.getElementById('edad').value;
    if (edad < 16 || edad > 100) {
        alert('La edad debe estar entre 16 y 100 años');
        return;
    }
    
    // Validar términos y condiciones
    if (!document.getElementById('terminos').checked) {
        alert('Debe aceptar los términos y condiciones');
        return;
    }
    
    // Si todo está correcto, puedes enviar el formulario
    alert('Registro exitoso!');
    // registerForm.submit(); // Descomentar para enviar realmente el formulario
});