const express = require('express');

const groupController = require('../controller/GroupController');

const router = express.Router();

router
    .route('/')
    .get(groupController.getAllGroup)
    .post(groupController.postGroup)
    .put(groupController.putGroup);

router
    .route('/:id')
    .get(groupController.getGroupById)
    .delete(groupController.deleteGroupById);

module.exports = router