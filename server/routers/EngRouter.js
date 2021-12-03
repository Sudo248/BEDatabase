const express  =require('express');
const engController = require('../controller/EngController');

const router = express.Router();

router
    .route('/')
    .get(engController.getAllEng)
    .post(engController.postEng)
    .put(engController.putEng)

router
    .route('/user')
    .get(engController.getEngByUserId)

router
    .route('/group')
    .get(engController.getEngByGroupId)

router
    .route('/type')
    .get(engController.getEngByType)

router
    .route('/:id')
    .get(engController.getEngById)
    .delete(engController.deleteEngById)

module.exports = router