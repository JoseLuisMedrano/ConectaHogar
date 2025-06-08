// Función para cambiar el navbar al hacer scroll
function navbarShrink() {
    const navbarCollapsible = document.getElementById('mainNav');
    if (!navbarCollapsible) return;
    
    if (window.scrollY === 0) {
        navbarCollapsible.classList.remove('navbar-shrink');
    } else {
        navbarCollapsible.classList.add('navbar-shrink');
    }
}

// Modal de selección de rol
function setupRoleModal() {
    const openModalBtn = document.getElementById('openRoleModalBtn');
    const roleModal = new bootstrap.Modal(document.getElementById('roleModal'));
    const roleOptions = document.querySelectorAll('.role-option');
    const confirmBtn = document.getElementById('confirmRoleBtn');
    let selectedRole = null;

    if (openModalBtn) {
        openModalBtn.addEventListener('click', function() {
            selectedRole = null;
            confirmBtn.disabled = true;
            
            // Resetear selecciones previas
            roleOptions.forEach(opt => {
                opt.classList.remove('selected');
                opt.style.borderColor = 'transparent';
                opt.style.backgroundColor = 'rgba(255,255,255,0.1)';
            });
            
            roleModal.show();
        });
    }

    // Manejar selección de rol
    roleOptions.forEach(option => {
        option.addEventListener('click', function() {
            // Remover selección previa
            roleOptions.forEach(opt => {
                opt.classList.remove('selected');
                opt.style.borderColor = 'transparent';
                opt.style.backgroundColor = 'rgba(255,255,255,0.1)';
            });

            // Seleccionar nueva opción
            selectedRole = this.getAttribute('data-role');
            this.classList.add('selected');
            this.style.borderColor = '#28a745';
            this.style.backgroundColor = 'rgba(40, 167, 69, 0.2)';
            
            // Habilitar botón de confirmación
            confirmBtn.disabled = false;
        });

        // Permitir selección con teclado
        option.addEventListener('keydown', function(e) {
            if (e.key === 'Enter' || e.key === ' ') {
                this.click();
            }
        });
    });

    // Confirmar selección y redirigir (aquí está tu cambio)
confirmBtn.addEventListener('click', function() {
        if (!selectedRole) return;
        
        if (selectedRole === 'cliente') {
            window.location.href = './1 - Registrarse/FormularioCliente/1 - PanelCliente/PanelCliente.html';
        } else if (selectedRole === 'trabajador') {
            window.location.href = './1 - Registrarse/FormularioTrabajador/1 - PanelTrabajador/PanelTrabajador.html';
        }
    });
}

// Cerrar el menú al hacer clic en un enlace (en móviles)
function setupMobileMenu() {
    document.querySelectorAll('#menu .nav-link').forEach(link => {
        link.addEventListener('click', () => {
            const navbarToggler = document.querySelector('.navbar-toggler');
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });
}

// Animación de las tarjetas al aparecer
function animateCards() {
    const cards = document.querySelectorAll('.category-card, .testimonio');
    cards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        
        setTimeout(() => {
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, 200 * index);
    });
}

// Efectos hover para botones
function setupHoverEffects() {
    const buttons = document.querySelectorAll('.contratar-btn, .trabajar-btn');
    
    buttons.forEach(btn => {
        btn.addEventListener('mouseenter', () => {
            btn.style.animation = 'vibrate 0.3s linear';
            setTimeout(() => {
                btn.style.animation = '';
            }, 300);
        });
    });
}

// Inicialización cuando el DOM está listo
document.addEventListener('DOMContentLoaded', function() {
    // Configurar navbar
    navbarShrink();
    document.addEventListener('scroll', navbarShrink);
    
    // Configurar modal de selección de rol
    setupRoleModal();
    
    // Configurar menú móvil
    setupMobileMenu();
    
    // Animación de tarjetas
    animateCards();
    
    // Efectos hover
    setupHoverEffects();
    
    // Smooth scrolling para enlaces
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    });
});