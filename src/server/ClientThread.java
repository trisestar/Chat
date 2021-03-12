package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;

public class ClientThread extends Thread implements Serializable {

   /* private User user = new User();
    private UserDB userDB = new UserDB();
    private ArrayList<User> listUser = new ArrayList<>();

    private Admin admin = new Admin();
    private AdminDB adminDB = new AdminDB();
    private ArrayList<Admin> listAdmin = new ArrayList<>();

    private ServiceContract service = new ServiceContract();
    private ServiceContractDB serviceDB = new ServiceContractDB();
    private ArrayList<ServiceContract> listService = new ArrayList<>();

    private ContractSale sale = new ContractSale();
    private ContractSaleDB saleDB = new ContractSaleDB();
    private ArrayList<ContractSale> listSale = new ArrayList<>();

    private EmploymentContract employment = new EmploymentContract();
    private EmploymentContractDB employmentDB = new EmploymentContractDB();
    private ArrayList<EmploymentContract> listEmployment = new ArrayList<>();

    private LicensedContract licensed = new LicensedContract();
    private LicensedContractDB licensedDB = new LicensedContractDB();
    private ArrayList<LicensedContract> listLicensed = new ArrayList<>();

    private Company company = new Company();
    private CompanyDB companyDB = new CompanyDB();
    private ArrayList<Company> listCompany = new ArrayList<>();

    private Client client = new Client();
    private ClientDB clientDB = new ClientDB();
    private ArrayList<Client> listClient = new ArrayList<>();

    private Contract contract = new Contract();
    private ContractDB contractDB = new ContractDB();
    private ArrayList<Contract> listContract = new ArrayList<>();*/

    private String ch;
    private String string;
    private int count;
    private Socket socket;
    public static int numOfUsers = 0;
    private int lastId = 0;


    public ClientThread(Socket socket) {
        this.socket = socket;
        this.start();
    }

    public void run() {
        try {

            Message message;
            ObjectOutputStream outputStream;
            ObjectInputStream inputStream;
            inputStream = new ObjectInputStream(this.socket.getInputStream());
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            String buf = "";
            while (true) {

                message = (Message) inputStream.readObject();
                ch = message.getCommand();
                //if (!message.getCommand().equals("check")) {
                    System.out.println("Получено: " + message.getCommand() + " " + message.getBuf());
                //}
                switch (ch) {

                    case "check":{
                        if (Integer.parseInt(message.getBuf()) != Chat.getSize()) {
                            message.setBuf(String.valueOf(Chat.getSize()));
                            message.setMessages(Chat.history);
                            System.out.println("Отправлено " + Chat.getSize());
                            outputStream.writeObject(message);

                            for (String str : Chat.history){
                                System.out.println(str);
                                System.out.println("-------");
                            }

                        } else {
                            message.setBuf("old");
                            //System.out.println("Отправлено old");
                            outputStream.writeObject(message);
                        }
                        break;
                    }

                    case "new": {
                        Chat.history.add(message.getBuf());
                        outputStream.writeObject(new Message(Chat.history));

                        break;
                    }

                    case "reload": {
                        outputStream.writeObject(new Message(Chat.history));
                        break;
                    }

                    default: {
                        System.out.println("Error");
                        break;
                    }





                    /*case "АвторизацияПользователя": {
                        User userData = (User) this.message.getStructure();
                        boolean flag = false;

                        listUser = userDB.getAll();
                        for (int i = 0; i < listUser.size(); i++) {
                            user = listUser.get(i);
                            if (userData.getLogin().equals(user.getLogin()) && userData.getPassword().equals(user.getPassword())) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag == true) {

                            System.out.println("В систему вошел пользователь: " + userData.getLogin());
                            System.out.println("Дата: " + Calendar.getInstance().getTime());
                            outputStream.writeObject(new Message("Успешно"));
                        } else {
                            outputStream.writeObject(new Message("Неудачно"));
                            System.out.println("Попытка войти в систему (login: "+ userData.getLogin()+")");
                            System.out.println("Дата: " + Calendar.getInstance().getTime());
                        }

                        break;
                    }
                    case "АвторизацияАдминистратора":{
                        Admin adminData = (Admin) this.message.getStructure();
                        boolean flag = false;

                        listAdmin = adminDB.getAll();
                        for (int i = 0; i < listAdmin.size(); i++) {
                            admin = listAdmin.get(i);
                            if (adminData.getLogin().equals(admin.getLogin()) && adminData.getPassword().equals(admin.getPassword())) {
                                flag = true;
                                break;
                            }
                        }
                        if (flag == true) {
                            System.out.println("В систему вошел администратор: " + adminData.getLogin());
                            System.out.println("Дата: " + Calendar.getInstance().getTime());
                            outputStream.writeObject(new Message("Успешно"));
                        } else {
                            outputStream.writeObject(new Message("Неудачно"));
                            System.out.println("Попытка войти в систему (login: "+ adminData.getLogin()+")");
                            System.out.println("Дата: " + Calendar.getInstance().getTime());
                        }
                        break;
                    }
                    /////////Удаление/////////////////////////////////////////////////////////////////
                    case "УдалениеПользователя":{
                        User userData = (User) this.message.getStructure();
                        userDB.delete(userData.getId());
                        System.out.println("Удален пользователь (id: "+ userData.getId()+")");
                        break;
                    }
                    case "УдалениеАдминистратора":{
                        Admin adminData = (Admin) this.message.getStructure();
                        adminDB.delete(adminData.getId());
                        System.out.println("Удален администратор (id: "+ admin.getId()+")");
                        break;
                    }
                    case "УдалениеService":{
                        ServiceContract serviceContract = (ServiceContract) this.message.getStructure();
                        serviceDB.delete(serviceContract.getId());
                        System.out.println("Удален договор обслуживания (id: "+serviceContract.getId()+")");
                        break;
                    }
                    case "УдалениеSale":{
                        ContractSale contractSale = (ContractSale) this.message.getStructure();
                        saleDB.delete(contractSale.getId());
                        System.out.println("Удален договор купли-продажи (id: "+contractSale.getId()+")");
                        break;
                    }
                    case "УдалениеEmployment":{
                        EmploymentContract employmentContract = (EmploymentContract) this.message.getStructure();
                        employmentDB.delete(employmentContract.getId());
                        System.out.println("Удален договор найма (id: "+employmentContract.getId()+")");
                        break;
                    }
                    case "УдалениеLicensed":{
                        LicensedContract licensedContract = (LicensedContract) this.message.getStructure();
                        licensedDB.delete(licensedContract.getId());
                        System.out.println("Удален лицензионный договор (id: "+licensedContract.getId()+")");
                        break;
                    }
                    case "УдалениеCompany":{
                        Company company = (Company) this.message.getStructure();
                        companyDB.delete(company.getIdcompany());
                        System.out.println("Удалена компани (id: "+company.getIdcompany()+")");
                        break;
                    }
                    /////////Регистрация////////////////////////////////////////////////////////
                    case "РегистрацияПользователя":{
                        User userData = (User) this.message.getStructure();
                        boolean bool = false;

                        listUser = userDB.getAll();
                        for (int i = 0; i < listUser.size(); i++) {
                            user = listUser.get(i);
                            if (userData.getLogin().equals(user.getLogin())) {
                                bool = true;
                                break;
                            }
                        }
                        if(bool == true){
                            outputStream.writeObject(new Message("Неудачно"));
                            System.out.println("Попытка регистрации: "+userData.getLogin());
                        }
                        else {
                            userDB.add(userData);
                            outputStream.writeObject(new Message("Успешно"));
                            System.out.println("Регистрация: "+userData.getLogin());
                        }
                        break;
                    }
                    case "РегистрацияАдминистратора":{
                        Admin adminData = (Admin) this.message.getStructure();
                        boolean bool = false;

                        listAdmin = adminDB.getAll();
                        for (int i = 0; i < listUser.size(); i++) {
                            admin = listAdmin.get(i);
                            if (adminData.getLogin().equals(admin.getLogin())) {
                                bool = true;
                                break;
                            }
                        }
                        if(bool == true){
                            outputStream.writeObject(new Message("Неудачно"));
                            System.out.println("Попытка регистрации: "+adminData.getLogin());
                        }
                        else {
                            adminDB.add(adminData);
                            outputStream.writeObject(new Message("Успешно"));
                            System.out.println("Регистрация: "+adminData.getLogin());
                        }
                        break;
                    }
                    //////////Вывод таблиц//////////////////////////////////////////////////////////////////
                    case "ТаблицаПользователей":{
                        listUser = userDB.getAll();
                        for (int i = 0; i < listUser.size(); i++) {
                            User user  = listUser.get(i);
                            outputStream.writeObject(new Message("п",user));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы User");
                        break;
                    }
                    case "ТаблицаАдминистраторов":{
                        listAdmin = adminDB.getAll();
                        for (int i = 0; i < listAdmin.size(); i++) {
                            Admin admin  = listAdmin.get(i);
                            outputStream.writeObject(new Message("п",admin));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы Admin");
                        break;
                    }
                    case "ТаблицаSale":{
                        listSale = saleDB.getAll();
                        for (int i = 0; i < listSale.size(); i++) {
                            ContractSale contractSale  = listSale.get(i);
                            outputStream.writeObject(new Message("",contractSale));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы Sale");
                        break;
                    }
                    case "ТаблицаLicensed":{
                        listLicensed = licensedDB.getAll();
                        for (int i = 0; i < listLicensed.size(); i++) {
                            LicensedContract licensedContract  = listLicensed.get(i);
                            outputStream.writeObject(new Message("",licensedContract));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы Licensed");
                        break;
                    }
                    case "ТаблицаEmployment":{
                        listEmployment = employmentDB.getAll();
                        for (int i = 0; i < listEmployment.size(); i++) {
                            EmploymentContract employmentContract  = listEmployment.get(i);
                            outputStream.writeObject(new Message("",employmentContract));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы Employment");
                        break;
                    }
                    case "ТаблицаService":{
                        listService = serviceDB.getAll();
                        for (int i = 0; i < listService.size(); i++) {
                            ServiceContract serviceContract  = listService.get(i);
                            outputStream.writeObject(new Message("",serviceContract));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы Service");
                        break;
                    }
                    case "ТаблицаPerson":{
                        listClient = clientDB.getAll();
                        for (int i = 0; i < listClient.size(); i++) {
                            Client client  = listClient.get(i);
                            outputStream.writeObject(new Message("",client));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы Person");
                        break;
                    }
                    case "ТаблицаCompany":{
                        listCompany = companyDB.getAll();
                        for (int i = 0; i < listCompany.size(); i++) {
                            Company company  = listCompany.get(i);
                            outputStream.writeObject(new Message("",company));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы Company");
                        break;
                    }
                    case "ТаблицаContract":{
                        listContract = contractDB.getAll();
                        for (int i = 0; i < listContract.size(); i++) {
                            Contract contract  = listContract.get(i);
                            outputStream.writeObject(new Message("",contract));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        System.out.println("Запрос на выдачу информации из таблицы Contract");
                        break;
                    }
                    ///////////////////////////////////////////////////////////////
                    case "ДобавлениеService":{
                        ServiceContract serviceData = (ServiceContract) this.message.getStructure();
                        serviceDB.add(serviceData);
                        outputStream.writeObject(new Message("Успешно"));
                        System.out.println("Добавлен договор обслуживания.");
                        break;
                    }
                    case "ДобавлениеSale":{
                        ContractSale contractSale = (ContractSale) this.message.getStructure();
                        saleDB.add(contractSale);
                        outputStream.writeObject(new Message("Успешно"));
                        System.out.println("Добавлен договор купли продажи.");
                        break;
                    }
                    case "ДобавлениеEmployment":{
                        EmploymentContract employmentContract = (EmploymentContract) this.message.getStructure();
                        employmentDB.add(employmentContract);
                        outputStream.writeObject(new Message("Успешно"));
                        System.out.println("Добавлен договор найма.");
                        break;
                    }
                    case "ДобавлениеLicensed":{
                        LicensedContract licensedContract = (LicensedContract) this.message.getStructure();
                        licensedDB.add(licensedContract);
                        outputStream.writeObject(new Message("Успешно"));
                        System.out.println("Добавлен лицензионный договор.");
                        break;
                    }
                    case "ДобавлениеCompany":{
                        Company company = (Company) this.message.getStructure();
                        companyDB.add(company);
                        outputStream.writeObject(new Message("Успешно"));
                        System.out.println("Добавлена компания ("+company.getCompanyname()+").");
                        break;
                    }
                    case "ДобавлениеPerson":{
                        Client client = (Client) this.message.getStructure();
                        clientDB.add(client);
                        outputStream.writeObject(new Message("Успешно"));
                        System.out.println("Добавлен представитель ("+client.getFirstname()+" "+client.getSecondname()+").");
                        break;
                    }
                    case "ДобавлениеContract":{
                        Contract contract = (Contract) this.message.getStructure();
                        contractDB.add(contract);
                        outputStream.writeObject(new Message("Успешно"));
                        break;
                    }
                    //////////////////////////////////////////////
                    case "НайтиCompany":{
                        Company find = (Company) message.getStructure();
                        CompanyDB db = new CompanyDB();
                        listCompany = db.getAll();
                        for (int i = 0; i < listCompany.size(); i++) {
                            Company company = listCompany.get(i);
                            if (Objects.equals(find.getCompanyname(), company.getCompanyname())) {
                                outputStream.writeObject(new Message("", company));
                                break;
                            }
                        }
                        break;
                    }
                    case "НайтиContract":{
                        Contract find = (Contract) message.getStructure();
                        ContractDB db =new ContractDB();
                        listContract = db.getAll();
                        for (int i = 0; i < listContract.size(); i++) {
                            Contract contract = listContract.get(i);
                            if (Objects.equals(find.getIdcompany(), contract.getIdcompany()) && Objects.equals(find.getDate(), contract.getDate()) &&
                                    Objects.equals(find.getDeadline(), contract.getDeadline())) {
                                outputStream.writeObject(new Message("", contract));
                                break;
                            }
                        }
                        break;
                    }
                    case "НайтиCompanyInContract":{
                        Company find = (Company) message.getStructure();
                        listService = serviceDB.getAll();
                        for (int i = 0; i < listService.size(); i++) {
                            ServiceContract contract  = listService.get(i);
                            if(find.getIdcompany()==contract.getIdcompany())
                                outputStream.writeObject(new Message("",contract));
                        }
                        outputStream.writeObject(new Message("Конец"));

                        listSale = saleDB.getAll();
                        for (int i = 0; i < listSale.size(); i++) {
                            ContractSale contract  = listSale.get(i);
                            if(find.getIdcompany()==contract.getIdcompany())
                                outputStream.writeObject(new Message("",contract));
                        }
                        outputStream.writeObject(new Message("Конец"));

                        listEmployment = employmentDB.getAll();
                        for (int i = 0; i < listEmployment.size(); i++) {
                            EmploymentContract contract  = listEmployment.get(i);
                            if(find.getIdcompany()==contract.getIdcompany())
                                outputStream.writeObject(new Message("",contract));
                        }
                        outputStream.writeObject(new Message("Конец"));

                        listLicensed = licensedDB.getAll();
                        for (int i = 0; i < listLicensed.size(); i++) {
                            LicensedContract contract  = listLicensed.get(i);
                            if(find.getIdcompany()==contract.getIdcompany())
                                outputStream.writeObject(new Message("",contract));
                        }
                        outputStream.writeObject(new Message("Конец"));
                        break;
                    }
                    case "AdminCompany":{

                    }
                    case "AdminStatistics":{

                    }
                    case "AdminContract":{

                    }*/

                    case "Выход": {
                        socket.close();
                        outputStream.close();
                        inputStream.close();

                        break;
                    }
                }

            }

        } catch (SocketException e) {
            //User user = (User) this.message.getStructure();
            System.out.println("Пользователь отсоединился");
            numOfUsers--;
            System.out.println("Дата: " + Calendar.getInstance().getTime());
            System.out.println("Всего пользователей: " + numOfUsers);
        } catch (EOFException e) {
            System.out.println("...");
            e.printStackTrace();
            try {
                socket.close();

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}


