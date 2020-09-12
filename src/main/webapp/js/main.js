const APP_VIEW = document.getElementById('app-view');

window.onload = function() {
    loadLogin();
}

//------------------------------Load Views---------------------------------

function loadLogin() {

    console.log('inside loadLogin()')

    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'login.view', true);
    xhr.send();

    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            APP_VIEW.innerHTML = xhr.responseText;
            configureLoginView();
        }
    }
}

function loadHome() {

    console.log('inside loadHome()');

    if (!localStorage.getItem('authUser')) {
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'home.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureHomeView();
        }
    }
}

function loadAllUsers() {
    console.log('inside loadAllUsers()');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'allUsers.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureAllUsersView();
        }
    }
    
}


//-------------------------------Configure Views------------------------------

function configureLoginView(){
    console.log("inside configureLoginView()");

    document.getElementById('login-message').setAttribute('hidden', true);
    document.getElementById('login-button-container').addEventListener('mouseover', validateLoginForm);
    document.getElementById('login').addEventListener('click', login);
}

function configureHomeView() {

    console.log('inside configureHomeView()');

    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;
    document.getElementById('all-users').addEventListener('click', loadAllUsers);
    document.getElementById('single-user').addEventListener('click', loadUser);
}

function configureAllUsersView(){

    console.log('inside configureAllUsersView()');


    // document.getElementById('home').addEventListener('click', loadhome);
    // document.getElementById('single-user').addEventListener('click', loadUser);
    getAllUsers();

}

//--------------------------------Operations-----------------------------------

function login(){
    
    console.log('inside login()');

    let un = document.getElementById('login-username').value;
    let pw = document.getElementById('login-password').value;

    let credentials = {
        username: un,
        password: pw
    }

    let credentialsJSON = JSON.stringify(credentials);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'auth');
    xhr.setRequestHeader('Content-type', 'application/json');
    xhr.send(credentialsJSON);

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {

            document.getElementById('login-message').setAttribute('hidden', true);
            localStorage.setItem('authUser', xhr.responseText);
            loadHome();

        } else if (xhr.readyState == 4 && xhr.status == 401) {

            document.getElementById('login-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('login-message').innerText = err.message;
        }
    }
}

function getAllUsers() {

    console.log('inside getAllUsers');
    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'users');
    xhr.send();

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {

            let usersContainer = document.getElementById('users-container');
            document.getElementById('users-message').setAttribute('hidden', true);
            requestArr = JSON.parse(xhr.responseText);

            let table = document.getElementById("user-table");
            table.removeChild(document.getElementById("user-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "user-list");
            table.appendChild(newBody);

            for(let i=0; i < requestArr.length; i++){

                let newRow = document.createElement("tr");

                newRow.innerHTML = "<td>" + requestArr[i].id + "</td>" +
                                    "<td>" + requestArr[i].username + "</td>" +
                                    "<td>" + requestArr[i].firstName + "</td>" +
                                    "<td>" + requestArr[i].lastName + "</td>" +
                                    "<td>" + requestArr[i].email + "</td>";

                newBody.appendChild(newRow);
            }

        } else if (xhr.readyState == 4 && xhr.status == 401) {

            document.getElementById('users-message').removeAttribute('hidden');
        }
    }
    
}




//----------------------Form Validation--------------------------------- 

function validateLoginForm() {

    console.log('in validateLoginForm()');

    let msg = document.getElementById('login-message').innerText;

    if (msg == 'User authentication failed!') {
        return;
    }

    let un = document.getElementById('login-username').value;
    let pw = document.getElementById('login-password').value;

    if (!un || !pw) {
        document.getElementById('login-message').removeAttribute('hidden');
        document.getElementById('login-message').innerText = 'You must provided values for all fields in the form!';
        document.getElementById('login').setAttribute('disabled', true);
    } else {
        document.getElementById('login').removeAttribute('disabled');
        document.getElementById('login-message').setAttribute('hidden', true);
    }

}