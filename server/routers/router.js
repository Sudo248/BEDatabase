const accountRouter = require('./AccountRouter');
const engRouter = require('./EngRouter');
const groupRouter = require('./GroupRouter');
const userRouter = require('./UserRouter');
const vnRouter = require('./VnRouter')
const isAuth = require('../middleware/AuthMiddleWare').isAuth;

module.exports = function router(app){

    app.use('/', accountRouter);
    app.use('/user',isAuth, userRouter);
    app.use('/eng',isAuth, engRouter);
    app.use('/group',isAuth, groupRouter);
    app.use('/vn', isAuth, vnRouter);

    app.use((err, req, res, next) => {

        console.log(err.stack);
        console.log(err.name);
        console.log(err.code);

        res.status(500).json({
            message: "Something went wrong"
        })

    });

}