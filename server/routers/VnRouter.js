const express = require('express');

const router = express.Router();

const vnController = require('../controller/VnController');

router
    .route('/')
    .put(vnController.putVn)
    .post(vnController.postVn)

router
    .route('/:id')
    .delete(vnController.deleteVn);

module.exports = router;