const AccountDB = require('../models/AccountDB');
const UserDB = require('../models/UserDB');
const bcrypt = require('bcrypt');
const validator = require('validator');
const authMethod = require('../util/AuthMethods');
const secretKey = process.env.JWT_KEY;
const tokenLife = process.env.JWT_TOKEN_LIFE;
const refreshTokenLife = process.env.JWT_REFRESH_TOthKEN_LIFE;
const SALT_ROUNDS = process.env.SALT_ROUNDS;

module.exports.signUp = async(req, res, next) => {
    try {
        const {
            email,
            password
        } = req.body;

        console.log(`email: ${email}, password: ${password}`)

        const [oldAccounts, _] = await AccountDB.getAccountByEmail(email);

        console.log(oldAccounts[0]);

        if(oldAccounts.length > 0){
            console.log("This account already sign up");
            return res.status(409).send("This account already sign up");
        }

        const hashPassword = bcrypt.hashSync(password, 10);
        let account = new AccountDB(null, email, hashPassword);
        // tao user cung voi account 
        const userDB = new UserDB(null, "user_name", null);
        await account.insert();
        await userDB.insert();
        console.log("create account: ",account.email,account.account_id);
        
        res.status(200).json({message : "Create success", account_id: -1});
        
    } catch (error) {
        next(error);
    }
}

module.exports.signIn = async(req, res, next) => {
    try {
        const {
            email,
            password
        } = req.body;
        
        console.log(`email: ${email}, password: ${password}`)

        const [accounts,_] = await AccountDB.getAccountByEmail(email);
        const account = accounts[0];

        if(!account){
            return res.status(401).send("Invalid account");
        }
        
        // const hashPassword = bcrypt.hashSync('admin', 10);
        // console.log(hashPassword)

        const isPasswordValid = bcrypt.compareSync(password, account.password);
        if(!isPasswordValid){
            console.log("Wrong password")
            return res.status(401).send("Wrong password")
        }

        const data = {
            account_id: account.account_id,
            email: account.email,
        }

        const token = await authMethod.genToken(
            data,
            secretKey,
            tokenLife,
        );

        if(!token){
            return res.status(401).send("Error when generate token");
        }

        console.log("Login success",account.account_id,token)
        res.status(200).json({message:"Login success",account_id:account.account_id,token});
        
        
    } catch (error) {
        next(error);
    }
}

module.exports.signOut = async(req, res, next) => {
    
}

module.exports.getAccount = async(req, res, next) => {
    try {
        
        const query = req.query;

        if(query.email){
            const [accounts, _] = await AccountDB.getAccountByEmail(query.email);
            res.status(200).json(accounts[0]);
        }else{
            const [accounts, _] = await AccountDB.getAllAccount();
            res.status(200).json(accounts);
        }


    } catch (error) {
        next(error);
    }
}

module.exports.getAccountById = async(req, res, next) => {
    try {
        
        const id = req.params.id;

        const [accounts, _] = await AccountDB.getAccountById(id);
        res.status(200).json(accounts[0]);
    
    } catch (error) {
        next(error);
    }
}

module.exports.postAccount = async(req, res, next) => {
    try {
        
        const {
            email,
            password
        } = req.body;

        const account = new AccountDB(null, email, password);

        await account.insert();

        res.status(201).json({message: "Insert account successs!"})

    } catch (error) {
        next(error);
    }
}

module.exports.putAccount = async(req, res, next) => {
    try {
        const {
            account_id,
            email,
            password
        } = req.body;

        const account = new AccountDB(account_id, email, password);

        await account.update();

        res.status(200).json({message: "Update account successs!"})

    } catch (error) {
        next(error);
    }
}

module.exports.changePassword = async(req, res, next) => {
    try {
        
        const {
            account_id,
            oldPassword,
            newPassword
        } = req.body;

        console.log(req.body)

        const [accounts, _] = await AccountDB.getAccountById(account_id);
        const account = accounts[0]

        console.log(account)

        const isPasswordValid = bcrypt.compareSync(oldPassword, account.password);

        if(!isPasswordValid){
            res.status(401).send("Wrong password");
        }
        else{
            const hashPassword = bcrypt.hashSync(newPassword, 10);
            await AccountDB.changePassword(account_id, hashPassword);
            console.log("Change password successs!");
            res.status(200).json({message: "Change password successs!", account_id: account_id});
        }

        
    } catch (error) {
        next(error);
    }
}
