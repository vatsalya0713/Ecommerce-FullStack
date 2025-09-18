document.addEventListener('DOMContentLoaded', () => {
    // Check for user login status and update navbar
    const token = localStorage.getItem('jwtToken');
    const username = localStorage.getItem('username'); // Assuming you store username on login

    if (token && username) {
        document.getElementById('login-nav-item').classList.add('d-none');
        document.getElementById('welcome-nav-item').classList.remove('d-none');
        document.getElementById('logout-nav-item').classList.remove('d-none');
        document.getElementById('username-display').textContent = username;
    }

    // Logout functionality
    const logoutButton = document.getElementById('logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', () => {
            localStorage.removeItem('jwtToken');
            localStorage.removeItem('username');
            // Optional: You might want to clear the cart too on logout
            // localStorage.removeItem('cart');
            window.location.href = 'index.html';
        });
    }
});