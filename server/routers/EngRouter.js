const express  =require('express');
const engController = require('../controller/EngController');

const router = express.Router();

router
    .route('/')
    .get(engController.getAllEng)
    .post(engController.postEng)
    .put(engController.putEng)

router
    .route('/:id')
    .get(engController.getEngById)
    .delete(engController.deleteEngById)

router
    .route('/:user_id')
    .get(engController.getEngByUserId)

router
    .route('/:group_id')
    .get(engController.getEngByGroupId)

router
    .route('/:type')
    .get(engController.getEngByType)

module.exports = router