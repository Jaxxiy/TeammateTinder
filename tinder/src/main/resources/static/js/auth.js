document.addEventListener('DOMContentLoaded', function() {
    // Обработка формы логина
    const loginForm = document.querySelector('form[th\\:action="@{/auth/login}"]');
    if (loginForm) {
        loginForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            const response = await submitForm(this, '/auth/login');
            if (response.token) {
                localStorage.setItem('jwtToken', response.token);
                window.location.href = '/home';
            }
        });
    }

    // Обработка формы регистрации
    const registerForm = document.querySelector('form[th\\:action="@{/auth/register}"]');
    if (registerForm) {
        registerForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            const response = await submitForm(this, '/auth/register');
            if (response.token) {
                localStorage.setItem('jwtToken', response.token);
                window.location.href = '/home';
            }
        });
    }
});

async function submitForm(form, url) {
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        return { error: 'An error occurred' };
    }
}