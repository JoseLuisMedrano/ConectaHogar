const container = document.querySelector('.container');
const registerBtn = document.querySelector('.register-btn');
const loginBtn = document.querySelector('.login-btn');

registerBtn.addEventListener('click', () => {
    container.classList.add('active');
});

loginBtn.addEventListener('click', () => {
    container.classList.remove('active');
});

//--------------Para el modal------------
const openModal = document.querySelector('.hero_cta');
const modal = document.querySelector('.modal');
const closeModal = document.querySelector('.modal_close');


openModal.addEventListener('click',(e) => {
    e.preventDefault();
    modal.classList.add('modal--show');
});

closeModal.addEventListener('click',(e) => {
    e.preventDefault();
    modal.classList.remove('modal--show');
});