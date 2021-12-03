const express = require('express');

const router = express.Router();

const validatorAccount = require('../util/Validator').validateAccount

const accountController = require('../controller/AccountController');


router
    .route('/signIn')
    .post(accountController.signIn);

router
    .route('/signUp')
    .post(accountController.signUp);


router
    .route('/changePassword')
    .put(accountController.changePassword);

router
    .route('/signOut')
    .get(accountController.signOut);

module.exports = router