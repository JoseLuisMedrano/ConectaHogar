// ==================================================
// script.js
// ==================================================

document.addEventListener('DOMContentLoaded', function () {
  // 1) Menú móvil: alternar abierto/cerrado
  const menuToggle = document.getElementById('menu-toggle');
  const navList = document.getElementById('nav-list');

  menuToggle.addEventListener('click', () => {
    navList.classList.toggle('open');
  });

  // 2) Botones del hero para hacer scroll a secciones
  const btnNosotros = document.getElementById('btn-nosotros');
  const btnProblema = document.getElementById('btn-problema');

  btnNosotros.addEventListener('click', () => {
    document.getElementById('nosotros').scrollIntoView({ behavior: 'smooth' });
  });

  btnProblema.addEventListener('click', () => {
    document.getElementById('problema').scrollIntoView({ behavior: 'smooth' });
  });

  // 3) Cerrar el menú móvil al hacer clic en cualquiera de sus enlaces del nav
  const enlacesNav = navList.querySelectorAll('a');
  enlacesNav.forEach((enlace) => {
    enlace.addEventListener('click', () => {
      if (navList.classList.contains('open')) {
        navList.classList.remove('open');
      }
    });
  });
});
