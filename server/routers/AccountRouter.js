const express = require('express');

const router = express.Router();

const validatorAccount = require('../util/Validator').validateAccount

const accountController = require('../controller/AccountController');

router
    .route('/signIn')
    .get(accountController.signIn);

router
    .route('/signUp')
    .post(accountController.signUp);

router
    .route('/signOut')
    .get(accountController.signOut);

module.exports = router