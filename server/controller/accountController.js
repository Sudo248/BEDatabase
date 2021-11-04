const AccountDB = require('../models/AccountDB');
const UserDB = require('../models/UserDB');
const genToken = require('../util/genToken');

module.exports.signUp = async(req, res, next) => {
    try {
        const {
            email,
            password
        } = req.body;

        const oldAccount = await AccountDB.getAccountByEmail(email);

        if(oldAccount){
            res.status(409).json({message : "This account already sign up"})
        }

        const account = new AccountDB(null, email, password);
        // tao user cung voi account 
        const userDB = new UserDB(account.account_id, "user_name", null);
        account = await account.insert();
        await userDB.insert();
        console.log("create account: ",account);
        // tao token de gui la cho user
        const token = genToken(account.account_id);

        res.status(200).json({message:"Create account success", token});


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

        const account = await AccountDB.getAccountByEmail(email);

        if(account){
            if(password === account.password){
                const token = genToken(account.account_id);
                res.status(200).json({message:"Login success",token});
            }else{
                res.status(400).json({message:"Wrong password"});
            }
        }else{
            res.status(400).json({message:"Invalid account"});
        }
        
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
