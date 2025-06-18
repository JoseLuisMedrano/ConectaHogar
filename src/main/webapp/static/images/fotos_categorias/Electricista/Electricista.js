document.addEventListener('DOMContentLoaded', function() {
    // Control del video de fondo
    const heroVideo = document.getElementById('heroVideo');
    
    // Intentar reproducir el video
    const playVideo = () => {
        heroVideo.play().catch(error => {
            console.log("Auto-play no permitido:", error);
            // Mostrar botón de play alternativo
            const playButton = document.createElement('button');
            playButton.innerHTML = '<i class="fas fa-play me-2"></i>Reproducir Video';
            playButton.className = 'btn btn-warning btn-lg mt-3';
            playButton.onclick = () => {
                heroVideo.play();
                playButton.remove();
            };
            document.querySelector('.hero-content').appendChild(playButton);
        });
    };
    
    // Reproducir al hacer clic en cualquier parte
    document.addEventListener('click', function() {
        playVideo();
    }, { once: true });
    
    // Efectos hover para tarjetas
    const cards = document.querySelectorAll('.servicio-card, .trabajador-card');
    
    cards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-10px)';
            this.style.boxShadow = '0 15px 30px rgba(0, 0, 0, 0.2)';
        });
        
        card.addEventListener('mouseleave', function() {
            this.style.transform = '';
            this.style.boxShadow = '0 5px 20px rgba(0, 0, 0, 0.1)';
        });
    });
    
    // Botones de acción
    const actionButtons = document.querySelectorAll('.servicio-card .btn, .trabajador-card .btn');
    
    actionButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const card = this.closest('.card');
            const title = card.querySelector('.card-title, .card-header h3').textContent;
            
            if(this.textContent.includes('perfil')) {
                alert(`Redirigiendo al perfil de ${title}`);
            } else {
                alert(`Solicitando servicio: ${title}`);
            }
        });
    });
    
    // Smooth scroll para enlaces internos
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if(target) {
                window.scrollTo({
                    top: target.offsetTop - 70,
                    behavior: 'smooth'
                });
            }
        });
    });
});