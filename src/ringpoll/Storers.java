/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author raj
 */
public class Storers {

    public static Storers storers;

    public int ID;
    public String firstName;
    public String lastName;
    public String email;
    public String userName;
    public String password;

    public static int userId;
    public List<String> userNameList = new ArrayList<String>();
    public ArrayList<String> passwordList = new ArrayList<String>();
    public ArrayList<String> tempImgUrlList = new ArrayList<String>();
    public ArrayList<String> tempImgNameList = new ArrayList<String>();

    public static Storers getInstance() {
        if (storers == null) {
            storers = new Storers();
        }
        return storers;
    }
    
    

    public Map<Integer, String> questionMap = new HashMap<Integer, String>();

    //public ArrayList<SingleOptionDto> singleOptionList = new ArrayList<SingleOptionDto>();
    public Map<Integer, Map<Integer, String>> optionMap = new HashMap<Integer, Map<Integer, String>>();

    public Map<Integer, ArrayList<SingleFeedOptionPanel>> AllFeedPanelMap = new HashMap<Integer, ArrayList<SingleFeedOptionPanel>>();

}
