// Esperar a que el DOM (la estructura de la página) esté completamente cargado
document.addEventListener('DOMContentLoaded', function () {
    'use strict';

    // ===================================================================
    // 1. LÓGICA PARA EL NAVBAR TRANSPARENTE QUE SE VUELVE SÓLIDO
    // ===================================================================
    const mainNav = document.getElementById('mainNav');

    if (mainNav) {
        // Función que se ejecuta cada vez que el usuario hace scroll
        const handleNavbarScroll = () => {
            // Si el scroll vertical es mayor a 50 píxeles...
            if (window.scrollY > 50) {
                // ...añade la clase que lo hace sólido
                mainNav.classList.add('navbar-scrolled');
            } else {
                // ...de lo contrario, quita la clase para que vuelva a ser transparente
                mainNav.classList.remove('navbar-scrolled');
            }
        };

        // Escuchar el evento de scroll en la ventana
        window.addEventListener('scroll', handleNavbarScroll);

        // Ejecutar la función una vez al cargar por si la página ya está con scroll
        handleNavbarScroll();
    }


    // ===================================================================
    // 2. LÓGICA PARA EL DESPLAZAMIENTO SUAVE (SMOOTH SCROLL)
    // ===================================================================
    
    // Seleccionar todos los enlaces del menú que apunten a una sección de la página (ej: href="#categorias")
    const scrollLinks = document.querySelectorAll('.nav-link[href^="#"]');

    scrollLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            // Prevenir el comportamiento por defecto (el salto brusco)
            e.preventDefault();

            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);

            if (targetElement) {
                // Desplazar la vista suavemente hasta el elemento de destino
                targetElement.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start' // Alinea la parte superior del elemento con la parte superior de la ventana
                });
            }
        });
    });

});