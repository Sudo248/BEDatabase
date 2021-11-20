const express = require('express');

const userController = require('../controller/UserContonller');

const router = express.Router();

router
    .route('/')
    .get(userController.getUser)
    .post(userController.postUser)
    .put(userController.putUser)

router
    .route('/:id')
    .get(userController.getUserById)

module.exports = router
