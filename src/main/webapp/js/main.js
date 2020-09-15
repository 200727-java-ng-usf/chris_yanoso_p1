const APP_VIEW = document.getElementById('app-view');

window.onload = function() {
    loadLogin();
    document.getElementById('toLogin').addEventListener('click', loadLogin);
    document.getElementById('toHome').addEventListener('click', loadHome);
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
    let authUser = JSON.parse(localStorage.getItem('authUser'));
            console.log(authUser.role);
            if (authUser.role == 'Admin'){
                loadAdminHome();
            } else if (authUser.role == 'Manager'){
                loadManagerHome();
            } else if (authUser.role == 'Employee'){
                loadEmployeeHome();
            } else {
                loadBadHome();
            }
}

function loadAdminHome() {

    console.log('inside loadAdminHome()');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'home.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureAdminHomeView();
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

function loadUser() {
    console.log('inside loadUser()');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'userById.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureUsersByIdView();
        }
    }
}

function loadRegister() {
    console.log('inside loadRegister()');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'register.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureRegisterView();
        }
    }
}
function loadTerminateUser() {
    console.log('inside loadTerminateUser()');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'terminate.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureTerminateView();
        }
    }
}
function loadUpdateUser() {
    console.log('inside loadUpdateUser()');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'updateUser.view');
    xhr.send();

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureUpdateUserView();
        }
    }
}

function loadManagerHome(){

    console.log('inside loadManagerHome()');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'home.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureManagerHomeView();
        }
    }
}

function loadEmployeeHome(){

}

function loadBadHome(){

}

function loadAllReimbursements(){
    console.log('inside loadAllReimbursements()');

    let xhr = new XMLHttpRequest();
    xhr.open('Get', 'allReimbursements.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureAllReimbursementsView();
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

function configureAdminHomeView() {

    console.log('inside configureAdminHomeView()');

    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;
    document.getElementById('all-users').addEventListener('click', loadAllUsers);
    document.getElementById('single-user').addEventListener('click', loadUser);
    document.getElementById('register').addEventListener('click', loadRegister);
    document.getElementById('terminate-user').addEventListener('click', loadTerminateUser);
    document.getElementById('update-user').addEventListener('click', loadUpdateUser)
}

function configureAllUsersView(){

    console.log('inside configureAllUsersView()');


    document.getElementById('home').addEventListener('click', loadAdminHome);
    document.getElementById('single-user').addEventListener('click', loadUser);
    getAllUsers();

}

function configureUsersByIdView(){

    console.log('inside configureUserByIdView');

    document.getElementById('home').addEventListener('click', loadAdminHome);
    document.getElementById('user-table').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', getUserById);
    document.getElementById('users-message').setAttribute('hidden', true);

}

function configureRegisterView(){

    console.log('inside configureRegisterView()');

    document.getElementById('reg-message').setAttribute('hidden', true);

    //document.getElementById('reg-username').addEventListener('blur', isUsernameAvailable);
   // document.getElementById('email').addEventListener('blur', isEmailAvailable);

    //document.getElementById('register').setAttribute('disabled', true);
   // document.getElementById('reg-button-container').addEventListener('mouseover', validateRegisterForm);
    document.getElementById('register').addEventListener('click', register);
}

function configureTerminateView(){
    console.log('inside configureTerminateView()');

    document.getElementById('home').addEventListener('click', loadAdminHome);
    document.getElementById('user-table').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', terminateUserById);
    document.getElementById('users-message').setAttribute('hidden', true);
}

function configureUpdateUserView(){
    console.log('inside configureUpdateUserView()');

    document.getElementById('home').addEventListener('click', loadAdminHome);
    document.getElementById('user-table').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', getUserById);
    document.getElementById('users-message').setAttribute('hidden', true);
    document.getElementById('update').addEventListener('click', updateUser);
}

function configureManagerHomeView(){
    console.log('inside configureMangerHomeView()');

    document.getElementById('all-reimbursements').addEventListener('click', loadAllReimbursements);
}

function configureAllReimbursementsView(){
    console.log('inside configureAllReimbursementsView()');

    document.getElementById('home').addEventListener('click', loadHome);
    //document.getElementById('single-reimbursement').addEventListener('click', loadReimbursemnt);
    getAllReimbursements();
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
                                    "<td>" + requestArr[i].email + "</td>" +
                                    "<td>" + requestArr[i].userRole + "</td>";

                newBody.appendChild(newRow);
            }

        } else if (xhr.readyState == 4 && xhr.status == 401) {

            document.getElementById('users-message').removeAttribute('hidden');
        }
    }
    
}

function getUserById() {
    console.log("inside getUserById()");

    let nu = document.getElementById('user-id').value;

    let sentString = "users?id=" + nu;

    let xhr = new XMLHttpRequest();

    xhr.open('GET', sentString);
    xhr.send();

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById('user-table').removeAttribute('hidden');
            document.getElementById('users-message').setAttribute('hidden', true);
            requestArr = JSON.parse(xhr.responseText);

            let table = document.getElementById("user-table");
            table.removeChild(document.getElementById("user-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "user-list");
            table.appendChild(newBody);
             let newRow = document.createElement("tr");

             newRow.innerHTML = "<td>" + requestArr.id + "</td>" +
                                "<td>" + requestArr.username + "</td>" +
                                "<td>" + requestArr.firstName + "</td>" +
                                "<td>" + requestArr.lastName + "</td>" +
                                "<td>" + requestArr.email + "</td>" +
                                "<td>" + requestArr.userRole + "</td>";

            newBody.appendChild(newRow);
        }else if (xhr.readyState == 4 && xhr.status == 401) {

            document.getElementById('users-message').removeAttribute('hidden');
        }
    }
}

function register() {

    console.log('inside register()');

    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;

    let newUser = {
        firstName: fn,
        lastName: ln,
        email: email,
        username: un,
        password: pw
    }

    let newUserJSON = JSON.stringify(newUser);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'users');
    xhr.send(newUserJSON);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 201) {
            loadHome();
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            document.getElementById('reg-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reg-message').innerText = err.message;
        }
    }
}

function terminateUserById() {
    console.log('inside terminateUserById()');
    let nu = document.getElementById('user-id').value;

    let sentString = "change/users?id=" + nu;

    let xhr = new XMLHttpRequest();

    xhr.open('GET', sentString);
    xhr.send();

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById('user-table').removeAttribute('hidden');
            document.getElementById('users-message').setAttribute('hidden', true);
            requestArr = JSON.parse(xhr.responseText);

            let table = document.getElementById("user-table");
            table.removeChild(document.getElementById("user-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "user-list");
            table.appendChild(newBody);
             let newRow = document.createElement("tr");

             newRow.innerHTML = "<td>" + requestArr.id + "</td>" +
                                "<td>" + requestArr.username + "</td>" +
                                "<td>" + requestArr.firstName + "</td>" +
                                "<td>" + requestArr.lastName + "</td>" +
                                "<td>" + requestArr.email + "</td>" +
                                "<td>" + requestArr.userRole + "</td>";

            newBody.appendChild(newRow);
        }else if (xhr.readyState == 4 && xhr.status == 401) {

            document.getElementById('users-message').removeAttribute('hidden');
        }
    }
}

function updateUser(){
    console.log('inside updateUser()');

    let id = document.getElementById('user-id').value;
    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;


    let newUser = {
        firstName: fn,
        lastName: ln,
        email: email,
        username: un,
        password: pw
    }

    let newUserJSON = JSON.stringify(newUser);
    let postString = "change/users/?id=" + id;

    let xhr = new XMLHttpRequest();

    xhr.open('POST', postString);
    xhr.send(newUserJSON);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            getUserById();
        } else if (xhr.readyState == 4 && xhr.status != 201) {
            document.getElementById('users-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('users-message').innerText = err.message;
        }
    }    
}

function getAllReimbursements() {
    console.log('inside getAllReimbursements');
    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'reimbursements');
    xhr.send();

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {

            let reimbursementContainer = document.getElementById('reimbursement-container');
            document.getElementById('reimbursement-message').setAttribute('hidden', true);
            requestArr = JSON.parse(xhr.responseText);

            let table = document.getElementById("reimbursement-table");
            table.removeChild(document.getElementById("reimbursement-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "reimbursement-list");
            table.appendChild(newBody);

            for(let i=0; i < requestArr.length; i++){

                let newRow = document.createElement("tr");

                newRow.innerHTML = "<td>" + requestArr[i].id + "</td>" +
                                    "<td>" + requestArr[i].amount + "</td>" +
                                    "<td>" + requestArr[i].submitted + "</td>" +
                                    "<td>" + requestArr[i].resolved + "</td>" +
                                    "<td>" + requestArr[i].description + "</td>" +
                                    "<td>" + requestArr[i].authorId + "</td>" +
                                    "<td>" + requestArr[i].resolverId + "</td>" +
                                    "<td>" + requestArr[i].reimbursementType + "</td>" +
                                    "<td>" + requestArr[i].reimbursementStatus + "</td>";

                newBody.appendChild(newRow);
            }

        } else if (xhr.readyState == 4 && xhr.status == 401) {

            document.getElementById('reimbursement-message').removeAttribute('hidden');
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
