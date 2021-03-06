const APP_VIEW = document.getElementById('app-view');

window.onload = function() {
    loadLogin();
    document.getElementById('toLogin').addEventListener('click', loadLogin);
    document.getElementById('toHome').addEventListener('click', loadHome);
    document.getElementById('toReimbursements').addEventListener('click', loadAllReimbursements);
    document.getElementById('toLogout').addEventListener('click', logout)
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
    console.log('inside loadEmployeeHome');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'home.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureEmployeeHomeView();
        }
    } 
}

function loadBadHome(){
    console.log('inside loadBadHome');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'home.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
        }
    } 
}

function loadAllReimbursements(){
    console.log('inside loadAllReimbursements()');

    if (!localStorage.getItem('authUser')) {
        console.log('No user logged in, navigating to login screen');
        loadLogin();
        return;
    }
    let authUser = JSON.parse(localStorage.getItem('authUser'));
            console.log(authUser.role);
            if (authUser.role == 'Admin'){
                loadALLUserReimbursements();
            } else if (authUser.role == 'Manager'){
                loadALLUserReimbursements();
            } else if (authUser.role == 'Employee'){
                loadALLUserReimbursements();
            } else {
                loadBadHome();
            }
}

function loadALLUserReimbursements(){

    
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



function loadReimbursementById() {
    console.log('inside loadReimbursementById()');

    let authUser = JSON.parse(localStorage.getItem('authUser'));
    if (authUser.role != "Manager"){
        loadUserReimbursementById();
    }
    let xhr = new XMLHttpRequest();
    xhr.open('Get', 'reimbursementById.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureReimbursementByIdView();
        }
    }
}

function loadUserReimbursementById() {
    console.log('inside loadUserReimbursementById()');

    let xhr = new XMLHttpRequest();
    xhr.open('Get', 'userReimbursementById.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureUserReimbursementByIdView();
        }
    }
}

function loadUpdateReimbursement(){
    console.log('inside loadUpdateReimbursement()');

    let xhr = new XMLHttpRequest();
    xhr.open('Get', 'updateReimbursement.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureUpdateReimbursementView();
        }
    }
}

function loadAllPendingReimbursements(){
    console.log('inside loadAllPendingReimbursements()');

    let xhr = new XMLHttpRequest();
    xhr.open('Get', 'allPendingReimbursements.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureAllPendingReimbursementsView();
        }
    }
}

function loadAllResolvedReimbursements(){
    console.log('inside loadAllResolvedReimbursements()');

    let xhr = new XMLHttpRequest();
    xhr.open('Get', 'allResolvedReimbursements.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureAllResolvedReimbursementsView();
        }
    }
}

function loadNewReimbursement() {
    console.log('inside loadNewReimbursement()');

    let xhr = new XMLHttpRequest();
    xhr.open('Get', 'newReimbursements.view');
    xhr.send();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            APP_VIEW.innerHTML = xhr.responseText;
            configureNewReimbursementsView();
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


    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('single-user').addEventListener('click', loadUser);
    getAllUsers();

}

function configureUsersByIdView(){

    console.log('inside configureUserByIdView');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('user-table').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', getUserById);
    document.getElementById('users-message').setAttribute('hidden', true);

}

function configureRegisterView(){

    console.log('inside configureRegisterView()');

    document.getElementById('reg-message').setAttribute('hidden', true);

    document.getElementById('register').setAttribute('disabled', true);
    document.getElementById('reg-button-container').addEventListener('mouseover', validateRegisterForm);
    document.getElementById('register').addEventListener('click', register);
}

function configureTerminateView(){
    console.log('inside configureTerminateView()');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('user-table').setAttribute('hidden', true);
    document.getElementById('t-id').addEventListener('click', terminateUserById);
    document.getElementById('users-message').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', getUserById);
}

function configureUpdateUserView(){
    console.log('inside configureUpdateUserView()');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('user-table').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', getUserById);
    document.getElementById('users-message').setAttribute('hidden', true);
    document.getElementById('update').addEventListener('click', updateUser);
}

function configureManagerHomeView(){
    console.log('inside configureMangerHomeView()');

    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;
    document.getElementById('all-reimbursements').addEventListener('click', loadAllReimbursements);
    document.getElementById('reimbursement-by-id').addEventListener('click', loadReimbursementById);
    document.getElementById('all-pending-reimbursements').addEventListener('click', loadAllPendingReimbursements);
    document.getElementById('all-resolved-reimbursements').addEventListener('click', loadAllResolvedReimbursements);
}

function configureAllReimbursementsView(){
    console.log('inside configureAllReimbursementsView()');

    document.getElementById('home').addEventListener('click', loadHome);
    let authUser = JSON.parse(localStorage.getItem('authUser'));
    if (authUser.role == "Manager"){
        document.getElementById('single-reimbursement').addEventListener('click', loadReimbursementById);
    } else {
        document.getElementById('single-reimbursement').addEventListener('click', loadUserReimbursementById);
    }
    getAllReimbursements();
}

function configureReimbursementByIdView(){
    console.log('inside configureReimbursementByIdView()');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('reimbursement-table').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', getReimbursementById);
    document.getElementById('reimbursement-message').setAttribute('hidden', true);
    document.getElementById('approve-deny').setAttribute('hidden', true);
}

function configureAllPendingReimbursementsView(){
    console.log('inside configureAllPendingReimbursementsView()');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('single-reimbursement').addEventListener('click', loadReimbursementById);
    getAllPendingReimbursements();
}

function configureAllResolvedReimbursementsView(){
    console.log('inside configureAllResolvedReimbursementsView()');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('single-reimbursement').addEventListener('click', loadReimbursementById);
    getAllResolvedReimbursements();
}

function configureEmployeeHomeView(){
    console.log('inside configureEmployeeHomeView()');

    let authUser = JSON.parse(localStorage.getItem('authUser'));
    document.getElementById('loggedInUsername').innerText = authUser.username;
    document.getElementById('all-reimbursements').addEventListener('click', loadAllReimbursements);
    document.getElementById('reimbursement-by-id').addEventListener('click', loadUserReimbursementById);
    document.getElementById('all-pending-reimbursements').addEventListener('click', loadAllPendingReimbursements);
    document.getElementById('all-resolved-reimbursements').addEventListener('click', loadAllResolvedReimbursements);
    document.getElementById('new-reimbursement').addEventListener('click', loadNewReimbursement);
    document.getElementById('update-reimbursement').addEventListener('click', loadUpdateReimbursement);
}

function configureUserReimbursementByIdView() {
    console.log('inside configureUserReimbursementByIdView()');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('reimbursement-table').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', getUserReimbursementById);
    document.getElementById('reimbursement-message').setAttribute('hidden', true);
    document.getElementById("update-button").addEventListener('click', loadUpdateReimbursement);
}

function configureNewReimbursementsView(){
    console.log('inside configureNewReimbursementsView()');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('reg-message').setAttribute('hidden', true);
    document.getElementById('register').setAttribute('disabled', true);
    document.getElementById('reg-button-container').addEventListener('mouseover', validateReimbursementForm);
    document.getElementById('register').addEventListener('click', registerNewReimbursement);
}

function configureUpdateReimbursementView(){
    console.log('inside configureUpdateReimbursementView()');

    document.getElementById('home').addEventListener('click', loadHome);
    document.getElementById('reimbursement-table').setAttribute('hidden', true);
    document.getElementById('search-id').addEventListener('click', getUserReimbursementById);
    document.getElementById('reimbursement-message').setAttribute('hidden', true);
    document.getElementById('update').addEventListener('click', updateReimbursement);

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
function sortJSON(requestArr) {
for (let i = 0; i < requestArr.length -1; i++){
    for (let j = 0; j < requestArr.length - i - 1; j++){
        if (requestArr[j].id > requestArr[j+1].id){
            let temp = requestArr[j];
            requestArr[j] = requestArr[j+1];
            requestArr[j+1] = temp;
        }
    }
}
return requestArr;
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
            requestArrUnSorted = JSON.parse(xhr.responseText);

            let table = document.getElementById("user-table");
            table.removeChild(document.getElementById("user-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "user-list");
            table.appendChild(newBody);
            requestArr = sortJSON(requestArrUnSorted);
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

        } else if (xhr.readyState == 4 && xhr.status != 200) {

            document.getElementById('users-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('users-message').innerText = err.message;
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
        }else if (xhr.readyState == 4 && xhr.status != 200) {

            document.getElementById('users-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('users-message').innerText = err.message;
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
    let type = document.querySelector('input[name = "type"]:checked').value;
    let newUser = {
        firstName: fn,
        lastName: ln,
        email: email,
        username: un,
        password: pw,
        userRole: type
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
        }else if (xhr.readyState == 4 && xhr.status != 200) {

            document.getElementById('users-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('users-message').innerText = err.message;
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
    let type = document.querySelector('input[name = "type"]:checked').value;


    let newUser = {
        firstName: fn,
        lastName: ln,
        email: email,
        username: un,
        password: pw,
        userRole: type
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
            requestArrUnSorted = JSON.parse(xhr.responseText);

            let table = document.getElementById("reimbursement-table");
            table.removeChild(document.getElementById("reimbursement-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "reimbursement-list");
            table.appendChild(newBody);
            requestArr = sortJSON(requestArrUnSorted);
            for(let i=0; i < requestArr.length; i++){
                let submitDate = new Date(requestArr[i].submitted);
                let resolvedDate = new Date(requestArr[i].resolved);
                if (resolvedDate == "Wed Dec 31 1969 19:00:00 GMT-0500 (Eastern Standard Time)"){
                    resolvedDate = null;
                }
                let newRow = document.createElement("tr");

                newRow.innerHTML = "<td>" + requestArr[i].id + "</td>" +
                                    "<td>" + requestArr[i].amount + "</td>" +
                                    "<td>" + submitDate + "</td>" +
                                    "<td>" + resolvedDate + "</td>" +
                                    "<td>" + requestArr[i].description + "</td>" +
                                    "<td>" + requestArr[i].authorId + "</td>" +
                                    "<td>" + requestArr[i].resolverId + "</td>" +
                                    "<td>" + requestArr[i].reimbursementType + "</td>" +
                                    "<td>" + requestArr[i].reimbursementStatus + "</td>";

                newBody.appendChild(newRow);
            }

        } else if (xhr.readyState == 4 && xhr.status != 200) {

            document.getElementById('reimbursement-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
             document.getElementById('reimbursement-message').innerText = err.message;
        }
    }
}

function getReimbursementById() {
    console.log('inside getReimbursementById');

    let nu = document.getElementById('reimbursement-id').value;

    let sentString = "reimbursements?id=" + nu;

    let xhr = new XMLHttpRequest();

    xhr.open('GET', sentString);
    xhr.send();

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById('reimbursement-table').removeAttribute('hidden');
            document.getElementById('reimbursement-message').setAttribute('hidden', true);
            requestArr = JSON.parse(xhr.responseText);

            let table = document.getElementById("reimbursement-table");
            table.removeChild(document.getElementById("reimbursement-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "reimbursement-list");
            table.appendChild(newBody);
             let newRow = document.createElement("tr");
             let submitDate = new Date(requestArr.submitted);
            let resolvedDate = new Date(requestArr.resolved);
            if (resolvedDate == "Wed Dec 31 1969 19:00:00 GMT-0500 (Eastern Standard Time)"){
                resolvedDate = null;
            }

             newRow.innerHTML = "<td>" + requestArr.id + "</td>" +
                                "<td>" + requestArr.amount + "</td>" +
                                "<td>" + submitDate + "</td>" +
                                "<td>" + requestArr.resolved + "</td>" +
                                "<td>" + requestArr.description + "</td>" +
                                "<td>" + requestArr.authorId + "</td>" +
                                "<td>" + requestArr.resolverId + "</td>" +
                                "<td>" + requestArr.reimbursementType + "</td>" +
                                "<td>" + requestArr.reimbursementStatus + "</td>";

            newBody.appendChild(newRow);
            if (requestArr.reimbursementStatus == "PENDING"){
                document.getElementById("approve-deny").removeAttribute('hidden');
                document.getElementById("approve").addEventListener('click', approveReimbursement);
                document.getElementById("deny").addEventListener('click', denyReimbursement);
            }
        }else if (xhr.readyState == 4 && xhr.status != 200) {

            document.getElementById('reimbursement-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reimbursement-message').innerText = err.message;
        }
    }
}

function approveReimbursement(){
    console.log('inside approveReimbursement()');

     let nu = document.getElementById('reimbursement-id').value;

     let sentString = "reimbursements?id=" + nu + "&status=approved";

     let xhr = new XMLHttpRequest();

     xhr.open('PUT', sentString);
     xhr.send();
     xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            getReimbursementById();
        } else if (xhr.readyState == 4 && xhr.status != 200) {
            document.getElementById('reimbursement-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reimbursement-message').innerText = err.message;
        }
    }
}

function denyReimbursement(){
    console.log('inside denyReimbursement()');

    let nu = document.getElementById('reimbursement-id').value;

     let sentString = "reimbursements?id=" + nu + "&status=denied";

     let xhr = new XMLHttpRequest();

     xhr.open('PUT', sentString);
     xhr.send();
     xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            getReimbursementById();
        } else if (xhr.readyState == 4 && xhr.status != 200) {
            document.getElementById('reimbursement-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reimbursement-message').innerText = err.message;
        }
    }
}

function getAllPendingReimbursements(){
    console.log('inside getAllPendingReimbursements');
    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'reimbursements?status=pending');
    xhr.send();

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {

            let reimbursementContainer = document.getElementById('reimbursement-container');
            document.getElementById('reimbursement-message').setAttribute('hidden', true);
            requestArrUnSorted = JSON.parse(xhr.responseText);

            let table = document.getElementById("reimbursement-table");
            table.removeChild(document.getElementById("reimbursement-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "reimbursement-list");
            table.appendChild(newBody);
            requestArr = sortJSON(requestArrUnSorted);
            for(let i=0; i < requestArr.length; i++){

                let newRow = document.createElement("tr");
                let submitDate = new Date(requestArr[i].submitted);
                let resolvedDate = new Date(requestArr[i].resolved);
                if (resolvedDate == "Wed Dec 31 1969 19:00:00 GMT-0500 (Eastern Standard Time)"){
                    resolvedDate = null;
                }

                newRow.innerHTML = "<td>" + requestArr[i].id + "</td>" +
                                    "<td>" + requestArr[i].amount + "</td>" +
                                    "<td>" + submitDate + "</td>" +
                                    "<td>" + resolvedDate + "</td>" +
                                    "<td>" + requestArr[i].description + "</td>" +
                                    "<td>" + requestArr[i].authorId + "</td>" +
                                    "<td>" + requestArr[i].resolverId + "</td>" +
                                    "<td>" + requestArr[i].reimbursementType + "</td>" +
                                    "<td>" + requestArr[i].reimbursementStatus + "</td>";

                newBody.appendChild(newRow);
            }

        } else if (xhr.readyState == 4 && xhr.status != 200) {

            document.getElementById('reimbursement-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
             document.getElementById('reimbursement-message').innerText = err.message;
        }
    }
}

function getAllResolvedReimbursements(){
    console.log('inside getAllResolvedReimbursements');
    let xhr = new XMLHttpRequest();

    xhr.open('GET', 'reimbursements?status=resolved');
    xhr.send();

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {

            let reimbursementContainer = document.getElementById('reimbursement-container');
            document.getElementById('reimbursement-message').setAttribute('hidden', true);
            requestArrUnSorted = JSON.parse(xhr.responseText);

            let table = document.getElementById("reimbursement-table");
            table.removeChild(document.getElementById("reimbursement-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "reimbursement-list");
            table.appendChild(newBody);
            requestArr = sortJSON(requestArrUnSorted);
            for(let i=0; i < requestArr.length; i++){
                let submitDate = new Date(requestArr[i].submitted);
                let resolvedDate = new Date(requestArr[i].resolved);
                if (resolvedDate == "Wed Dec 31 1969 19:00:00 GMT-0500 (Eastern Standard Time)"){
                    resolvedDate = null;
                }

                let newRow = document.createElement("tr");

                newRow.innerHTML = "<td>" + requestArr[i].id + "</td>" +
                                    "<td>" + requestArr[i].amount + "</td>" +
                                    "<td>" + submitDate + "</td>" +
                                    "<td>" + resolvedDate + "</td>" +
                                    "<td>" + requestArr[i].description + "</td>" +
                                    "<td>" + requestArr[i].authorId + "</td>" +
                                    "<td>" + requestArr[i].resolverId + "</td>" +
                                    "<td>" + requestArr[i].reimbursementType + "</td>" +
                                    "<td>" + requestArr[i].reimbursementStatus + "</td>";

                newBody.appendChild(newRow);
            }

        } else if (xhr.readyState == 4 && xhr.status != 200) {

            document.getElementById('reimbursement-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
             document.getElementById('reimbursement-message').innerText = err.message;
        }
    }
}

function getUserReimbursementById(){
    console.log('inside getUserReimbursementById');

    let nu = document.getElementById('reimbursement-id').value;

    let sentString = "reimbursements?id=" + nu;

    let xhr = new XMLHttpRequest();

    xhr.open('GET', sentString);
    xhr.send();

    xhr.onreadystatechange = function (){
        if (xhr.readyState == 4 && xhr.status == 200) {
            document.getElementById('reimbursement-table').removeAttribute('hidden');
            document.getElementById('reimbursement-message').setAttribute('hidden', true);
            requestArr = JSON.parse(xhr.responseText);

            let table = document.getElementById("reimbursement-table");
            table.removeChild(document.getElementById("reimbursement-list"));
            let newBody = document.createElement("tbody");
            newBody.setAttribute("id", "reimbursement-list");
            table.appendChild(newBody);
             let newRow = document.createElement("tr");
             let submitDate = new Date(requestArr.submitted);
            let resolvedDate = new Date(requestArr.resolved);
            if (resolvedDate == "Wed Dec 31 1969 19:00:00 GMT-0500 (Eastern Standard Time)"){
                resolvedDate = null;
            }

             newRow.innerHTML = "<td>" + requestArr.id + "</td>" +
                                "<td>" + requestArr.amount + "</td>" +
                                "<td>" + submitDate + "</td>" +
                                "<td>" + requestArr.resolved + "</td>" +
                                "<td>" + requestArr.description + "</td>" +
                                "<td>" + requestArr.authorId + "</td>" +
                                "<td>" + requestArr.resolverId + "</td>" +
                                "<td>" + requestArr.reimbursementType + "</td>" +
                                "<td>" + requestArr.reimbursementStatus + "</td>";

            newBody.appendChild(newRow);
            
        }else if (xhr.readyState == 4 && xhr.status != 200) {

            document.getElementById('reimbursement-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reimbursement-message').innerText = err.message;
        }
    }
}

function registerNewReimbursement() {
    console.log('inside registerNewReimbursement()');

    let am = document.getElementById('am').value;
    let de = document.getElementById('de').value;
    let type = document.querySelector('input[name = "type"]:checked').value;
    let newReimbursement = {
        amount: am,
        description: de,
        reimbursementType: type
    }

    let newReimbursementJSON = JSON.stringify(newReimbursement);

    let xhr = new XMLHttpRequest();

    xhr.open('POST', 'reimbursements');
    xhr.send(newReimbursementJSON);

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

function updateReimbursement() {
    console.log('inside registerNewReimbursement()');

    let id = document.getElementById('reimbursement-id').value;
    let am = document.getElementById('am').value;
    let de = document.getElementById('de').value;
    let type = document.querySelector('input[name = "type"]:checked').value;
    let newReimbursement = {
        amount: am,
        description: de,
        reimbursementType: type
    }

    let newReimbursementJSON = JSON.stringify(newReimbursement);
    console.log(newReimbursement);
    let xhr = new XMLHttpRequest();

    let sentString = 'reimbursements?id=' + id;
    xhr.open('PUT', sentString);
    xhr.send(newReimbursementJSON);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            getUserReimbursementById();
        } else if (xhr.readyState == 4 && xhr.status != 200) {
            document.getElementById('reimbursement-message').removeAttribute('hidden');
            let err = JSON.parse(xhr.responseText);
            document.getElementById('reimbursement-message').innerText = err.message;
        }
    }
}
function logout(){
    console.log('inside logout()');

    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'auth');
    xhr.send()

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 204){
            localStorage.removeItem('authUser');
            loadLogin();
        } else {
            console.log(JSON.parse(xhr.responseText));
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

function validateRegisterForm() {

    console.log('inside validateRegisterForm()');

    let fn = document.getElementById('fn').value;
    let ln = document.getElementById('ln').value;
    let email = document.getElementById('email').value;
    let un = document.getElementById('reg-username').value;
    let pw = document.getElementById('reg-password').value;
    let type = document.querySelector('input[name = "type"]:checked').value; 

    if (!fn || !ln || !email || !un || !pw || !type) {
        document.getElementById('reg-message').removeAttribute('hidden');
        document.getElementById('reg-message').innerText = 'You must provided values for all fields in the form!'
        document.getElementById('register').setAttribute('disabled', true);
    } else {
        document.getElementById('register').removeAttribute('disabled');
        document.getElementById('reg-message').setAttribute('hidden', true);
    }
}

function validateReimbursementForm() {

    console.log('inside validateReimbursementForm()');

    let am = document.getElementById('am').value;
    let de = document.getElementById('de').value;
    let type = document.querySelector('input[name = "type"]:checked').value;

    if (!am || !de || !type){
        document.getElementById('reg-message').removeAttribute('hidden');
        document.getElementById('reg-message').innerText = 'You must provided values for all fields in the form!'
        document.getElementById('register').setAttribute('disabled', true);
    } else {
        document.getElementById('register').removeAttribute('disabled');
        document.getElementById('reg-message').setAttribute('hidden', true);
    }
}
