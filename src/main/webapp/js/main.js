// Función para cambiar el navbar al hacer scroll
function navbarShrink() {
    const navbarCollapsible = document.getElementById('mainNav');
    if (!navbarCollapsible) {
        return;
    }
    if (window.scrollY === 0) {
        navbarCollapsible.classList.remove('navbar-shrink');
    } else {
        navbarCollapsible.classList.add('navbar-shrink');
    }
}

// Ejecutar la función al cargar la página
document.addEventListener('DOMContentLoaded', function() {
    // Aplicar el efecto de navbar al cargar
    navbarShrink();
    
    // Ejecutar la función al hacer scroll
    document.addEventListener('scroll', navbarShrink);
    
    // Cerrar el menú al hacer clic en un enlace (en móviles)
    document.querySelectorAll('#menu .nav-link').forEach(link => {
        link.addEventListener('click', () => {
            const navbarToggler = document.querySelector('.navbar-toggler');
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });
});
