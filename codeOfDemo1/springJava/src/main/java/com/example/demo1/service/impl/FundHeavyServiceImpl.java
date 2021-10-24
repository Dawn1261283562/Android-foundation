package com.example.demo1.service.impl;

import com.example.demo1.dao.FundHeavyDao;
import com.example.demo1.entity.FundHeavy;
//import com.example.demo1.entity.Instance;
import com.example.demo1.entity.FundHeavyInfo;
import com.example.demo1.service.FundHeavyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class FundHeavyServiceImpl implements FundHeavyService {
    @Autowired
    private FundHeavyDao fundHeavyDao;

    int ten=10;
    int toInt=1000;
    double concentrationRatio=1.25;
    @Override
    public List<FundHeavy> getListAll() {
        List<FundHeavy> listAll = fundHeavyDao.getListAll();
        return listAll;
    }

    @Override
    public List<FundHeavy> getListByStockList(int num,String[] stockIdList) {
        //全部元组
        List<FundHeavy> all_fund=resetListAll();
        //计分
        for (int i = 0; i < all_fund.size(); i++) {
            FundHeavy s = all_fund.get(i);
            for(int j=0;j<num;j++){
                for(int p=0;p<10;p++){
                    if(Objects.equals(s.get_stock_id()[p], stockIdList[j])){
                        s.score++;
                    }
                }
            }
        }
        
        //排序
        Collections.sort(all_fund);
        
        //取十个（这里暂时硬编码，不好）
        List<FundHeavy> m_fund=new ArrayList<FundHeavy>();
        for (int i = 0; i < 10; i++) {
            FundHeavy s = (FundHeavy)all_fund.get(i);
            m_fund.add(s);
        }
        
        //返回
        return m_fund;
    }


    /*
    这个逻辑目前是 先把 满足全部心仪股票的 基金筛出来，再进行评分。
    而不是直接评分。 筛出来的基金 心仪股票都要有的。
     */
    @Override
    public List<FundHeavy> getListByStockScore(int num,String[] stockIdList,String[] stockRadioList) {

        List<FundHeavy> all_fund=resetListAll();
        List<FundHeavy> m_fund=new ArrayList<FundHeavy>();

        for (int i = 0; i < all_fund.size(); i++) {
            FundHeavy s = (FundHeavy)all_fund.get(i);
            int count=0;
            for(int j=0;j<num;j++){
                for(int p=0;p<10;p++){
                    //System.out.print(1);System.out.print(stockIdList);
                    if(Objects.equals(s.get_stock_id()[p], stockIdList[j])){
                        count++;

                    }
                }
            }
            //筛出来了
            if(count!=0){
                int u= num-count;
                s.score=s.score+u*10;
                m_fund.add(s);
            }

        }

        //评分
        for (int i = 0; i < m_fund.size(); i++) {
            FundHeavy s = (FundHeavy)m_fund.get(i);
            for(int j=0;j<num;j++){
                for(int p=0;p<10;p++){
                    if(Objects.equals(s.get_stock_id()[p], stockIdList[j])){
                        //System.out.println(s.score);
                        double expectRadio=0;

                        expectRadio= Double.parseDouble(stockRadioList[j].toString());
                        //System.out.println(expectRadio);
                        double realRadio =0;

                        realRadio= Double.parseDouble(s.get_stock_ratio()[p].toString());
                        //System.out.println(realRadio);
                        double difference=0;

                        difference= expectRadio-realRadio;
                        //System.out.println(difference);
                        double dif=difference*100;//差异值 有+有-
                        //System.out.println(dif);
                        int theScore=(int)dif;
                        //System.out.println(theScore);
                        //取绝对值
                        if(theScore>=0){
                            s.score=(double)(theScore)+s.score;
                        }
                        else {
                            s.score=s.score-(double)(theScore);
                        }
                    }
                }
            }
        }
        //排序
        Collections.sort(m_fund);
        List<FundHeavy> m_m_fund=new ArrayList<FundHeavy>();
        //这里有可能不满十个，甚至一个都没有的情况，
        if(m_fund.size()-1-10>=0){
            for (int i = m_fund.size()-1; i > m_fund.size()-1-10; i--) {
                FundHeavy s = (FundHeavy)m_fund.get(i);

                m_m_fund.add(s);
            }
        }
        else{
            for (int i = m_fund.size()-1; i >0; i--) {
                FundHeavy s = (FundHeavy)m_fund.get(i);
                m_m_fund.add(s);
            }
        }

        return m_m_fund;
//        List<FundHeavy> m_m_m_fund=new ArrayList<FundHeavy>();
//        if(m_m_fund.size()-1-10>=0){
//            for (int i = m_m_fund.size()-1; i > m_m_fund.size()-1-10; i--) {
//                FundHeavy s = (FundHeavy)m_m_fund.get(i);
//
//                m_m_m_fund.add(s);
//            }
//        }
//        else{
//            for (int i = m_m_fund.size()-1; i >0; i--) {
//                FundHeavy s = (FundHeavy)m_m_fund.get(i);
//                m_m_m_fund.add(s);
//            }
//        }
//        return m_m_m_fund;
    }

    //下面大致是模式三的逻辑，不过实际上只是比较数量，再相同数量的板块还未比较大小，这个后面再写
    @Override
    public List<FundHeavy> getListByStockType(int num, String[] TypeList) {
        //全部元组
        List<FundHeavy> all_fund=fundHeavyDao.getListAll();
        for(FundHeavy fundHeavy:all_fund){
            fundHeavy.score=0;//注意这里的fundHeavy是单例，所以要清零。
            Set<String> set=fundHeavy.get_stock_type();
            for(int j=0;j<TypeList.length;j++){
                if(set.contains(TypeList[j]))fundHeavy.score++;
            }
        }
        Collections.sort(all_fund, new Comparator<FundHeavy>() {
            @Override
            public int compare(FundHeavy o1, FundHeavy o2) {
                return (int)o2.score-(int)o1.score;
            }
        });
        List<FundHeavy> ans=new ArrayList<>();
        for(int i=0;i<num;i++){
            ans.add(all_fund.get(i));
        }
        return ans;
    }


    //模式3+
    @Override
    public List<FundHeavy> getListByStockAllType(int num, String[] TypeList) {
        List<FundHeavy> m_fund = resetListAll();
        for (int i = 0; i < m_fund.size(); i++) {
            FundHeavy s = (FundHeavy)m_fund.get(i);
            for(int n=0;n<num;n++) {
                String n1=TypeList[n];

                String[] s11 = s.get_stock_all_Type();
                int si_size=s11.length;

                for (int p = 0; p < si_size; p++) {
                    String s1 = s.get_stock_all_Type()[p];
                    //boolean status = status;
//                    System.out.println(s1);
//                    System.out.println(n1);
                    if(s1==null)continue;
                    if(s1.contains(n1)){
                        s.score++;
                    }

                }
            }
            //m_fund.add(s);
        }

        Collections.sort(m_fund);
        List<FundHeavy> m_m_fund=new ArrayList<FundHeavy>();
        //m_m_fund=m_fund;
        //这里有可能不满十个，甚至一个都没有的情况，
        if(m_fund.size()-1-10>=0){
            for (int i = 0; i < 10; i++) {
                FundHeavy s = (FundHeavy)m_fund.get(i);

                m_m_fund.add(s);
            }
        }
        else{
            for (int i = 0; i <m_fund.size(); i--) {
                FundHeavy s = (FundHeavy)m_fund.get(i);
                m_m_fund.add(s);
            }
        }

        return m_m_fund;

    }

    @Override
    public List<FundHeavy> getListByStockAllTypeRadio(int num, String[] TypeList) {
        List<FundHeavy> m_fund = resetListAll();
        return getListByStockAllTypeRadio1(num,m_fund,TypeList);
        //对List评分
//        for (int fundNum = 0; fundNum < m_fund.size(); fundNum++) {
//            FundHeavy fund = (FundHeavy)m_fund.get(fundNum);
//            String[] type_list = fund.get_stock_all_Type();
//            int type_list_size=type_list.length;
//            //用户选择多种板块纳入考量
//            for (int p = 0; p < type_list_size; p++) {
//                String one_typeSet = fund.get_stock_all_Type()[p];
//                String one_radio=fund.get_stock_ratio()[p];
//                if(one_radio==null)continue;
//                int matchingDegreeOfNum =0;
//                //统计各持仓板块集中度
//                for(int n=0;n<num;n++) {
//                    String wanted=TypeList[n];
//                    if(one_typeSet==null)continue;
//
//                    if(one_typeSet.contains(wanted)){
//                        matchingDegreeOfNum++;
//                    }
//                }
//                int matchingDegree_rewardMechanism =(int)Math.pow(matchingDegreeOfNum,concentrationRatio)*toInt;
//                double expectRadio= Double.parseDouble(one_radio.toString());
//                int scoreSum=(int)(matchingDegree_rewardMechanism*expectRadio*toInt);
//                if(matchingDegree_rewardMechanism>=1)fund.score=fund.score+scoreSum;
//            }
//        }
//        Collections.sort(m_fund);//评分排序
//        List<FundHeavy> m_m_fund=new ArrayList<FundHeavy>();//打包数据给controller层
//        //这里有可能不满十个，甚至一个都没有的情况，
//        if(m_fund.size()-1-10>=0){
//            for (int i = 0; i < 10; i++) {
//                FundHeavy s = (FundHeavy)m_fund.get(i);
//
//                m_m_fund.add(s);
//            }
//        }
//        else for (int i = 0; i <m_fund.size(); i--)m_m_fund.add((FundHeavy)m_fund.get(i));
//        return m_m_fund;
    }

    @Override
    public List<FundHeavy> getListByStockAllTypeRadio1(int num,List<FundHeavy> FundList,String[] TypeList){
        for (int fundNum = 0; fundNum < FundList.size(); fundNum++) {
            FundHeavy fund = (FundHeavy)FundList.get(fundNum);
            String[] type_list = fund.get_stock_all_Type();
            int type_list_size=type_list.length;
            //用户选择多种板块纳入考量
            for (int p = 0; p < type_list_size; p++) {
                String one_typeSet = fund.get_stock_all_Type()[p];
                String one_radio=fund.get_stock_ratio()[p];
                if(one_radio==null)continue;
                int matchingDegreeOfNum =0;
                //统计各持仓板块集中度
                for(int n=0;n<num;n++) {
                    String wanted=TypeList[n];
                    if(one_typeSet==null)continue;

                    if(one_typeSet.contains(wanted)){
                        matchingDegreeOfNum++;
                    }
                }
                int matchingDegree_rewardMechanism =(int)Math.pow(matchingDegreeOfNum,concentrationRatio)*toInt;
                double expectRadio= Double.parseDouble(one_radio.toString());
                int scoreSum=(int)(matchingDegree_rewardMechanism*expectRadio*toInt);
                if(matchingDegree_rewardMechanism>=1)fund.score=fund.score+scoreSum;
            }
        }
        Collections.sort(FundList);//评分排序
        List<FundHeavy> m_m_fund=new ArrayList<FundHeavy>();//打包数据给controller层
        //这里有可能不满十个，甚至一个都没有的情况，
        if(FundList.size()-1-10>=0){
            for (int i = 0; i < 10; i++) {
                FundHeavy s = (FundHeavy)FundList.get(i);

                m_m_fund.add(s);
            }
        }
        else for (int i = 0; i <FundList.size(); i--)m_m_fund.add((FundHeavy)FundList.get(i));
        return m_m_fund;
    }
    //模式4，筛选出指定公司的基金，再使用模式三
    @Override
    public List<FundHeavy> getListByCompanyAndTypeRadio(int num, String[] TypeList,String company) {
        List<String> fundIdList = fundHeavyDao.getIdListByCompany(company);
        List<FundHeavy> all_fund = resetListAll();
        List<FundHeavy> m_fund = new ArrayList<FundHeavy>();
        for(FundHeavy fundHeavy:all_fund){
            for(String str:fundIdList){
                if(fundHeavy.getId().equals(str))m_fund.add(fundHeavy);
            }
        }
        return getListByStockAllTypeRadio1(num,m_fund,TypeList);
    }

    //普通搜索
    //普通搜索
    @Override
    public List<FundHeavyInfo> getListByGeneralSearch(String str) {
        String regex = "\\d{6}.OF";
        String regex1 = "\\d{6}.of";
        String regex2 = "\\d{6}.oF";
        String regex3 = "\\d{6}.Of";
        String regex4 = "\\d{6}.Of";
        String regex5 = "\\d{6}";
        if(str.matches(regex)||str.matches(regex1)||str.matches(regex2)||str.matches(regex3)||str.matches(regex4)){

            String temp1=str.substring(0,6);String temp2=str.substring(7,9);
            temp2=temp2.toUpperCase();
            str=temp1+'.'+temp2;
            System.out.println(str);
            return fundHeavyDao.getFundHeavyInfoById(str);
            }
        else if(str.matches(regex5)){
            str=str+".Of";
            return fundHeavyDao.getFundHeavyInfoById(str);
            }
        else return fundHeavyDao.getFundHeavyInfoByNotId(str);

    }

    @Override
    public List<FundHeavy> getById(String id) {

        List<FundHeavy> m_fund=fundHeavyDao.getListAll();

        for(FundHeavy fundHeavy:m_fund){
            if(Objects.equals(fundHeavy.id, id)){
                FundHeavy temp=fundHeavy;
                List<FundHeavy> m_fund1=new ArrayList<FundHeavy>();
                m_fund1.add(temp);
                return m_fund1;
            }
        }
        FundHeavy temp=null;
        List<FundHeavy> m_fund1=new ArrayList<FundHeavy>();
        m_fund1.add(temp);
        return m_fund1;


    }

    @Override
    public List<FundHeavy> resetListAll() {
        List<FundHeavy> m_fund=fundHeavyDao.getListAll();
        //注意这里的fundHeavy是单例，所以要清零
        for(FundHeavy fundHeavy:m_fund)fundHeavy.score=0;
        return m_fund;
    }

    //测试用
//    @Override
//    public FundHeavy getFundHeavy() {
//        return fundHeavyDao.getFundHeavy();
//    }
}
//    String regex = "\\d{6}.OF";
//    String regex1 = "\\d{6}.of";
//    String regex2 = "\\d{6}.oF";
//    String regex3 = "\\d{6}.Of";
//    String regex4 = "\\d{6}.Of";
//    String regex5 = "\\d{6}";
//        if(str.matches(regex)){
//
//                String temp1=str.substring(0,6);String temp2=str.substring(7,9);
//                temp2=temp2.toUpperCase();
//                str=temp1+'.'+temp2;
//                System.out.println(str);
//                return fundHeavyDao.getFundHeavyInfoById(str);
//                }
//                else if(str.matches(regex5)){
//                str=str+".Of";
//                return fundHeavyDao.getFundHeavyInfoById(str);
//                }
//                else return fundHeavyDao.getFundHeavyInfoByNotId(str);