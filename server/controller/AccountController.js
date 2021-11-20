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

        console.log(oldAccounts);

        if(oldAccounts.length > 0){
            return res.status(409).json({message : "This account already sign up"})
        }

        const hashPassword = bcrypt.hashSync(password, 10);
        const account = new AccountDB(null, email, hashPassword);
        // tao user cung voi account 
        const userDB = new UserDB(account.account_id, "user_name", null);
        account = await account.insert();
        await userDB.insert();
        console.log("create account: ",account.email);
        // tao token de gui la cho user
        return res.status(200).json({message:"Create account success", email});
        
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
            return res.status(401).json({message:"Invalid account"});
        }
        const hashPassword = bcrypt.hashSync('admin', 10);
        console.log(hashPassword)

        const isPasswordValid = bcrypt.compareSync(password, account.password);
        if(!isPasswordValid){
            return res.status(401).json({message:"Wrong password"});
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
            return res.status(401).json({message:"Error when generate token"});
        }
        
        return res.status(200).json({message:"Login success",email,token});
    
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
            const [account, _] = await AccountDB.getAccountByEmail(query.email);
            res.status(200).json({account});
        }else{
            const [accounts, _] = await AccountDB.getAllAccount();
            res.status(200).json({length:accounts.length, accounts});
        }


    } catch (error) {
        next(error);
    }
}

module.exports.getAccountById = async(req, res, next) => {
    try {
        
        const id = req.params.id;

        const [account, _] = await AccountDB.getAccountById(id);
        res.status(200).json({account});
    
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
