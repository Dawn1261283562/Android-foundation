package com.example.demo1.dao.impl;

import com.example.demo1.dao.FundHeavyDao;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.FundHeavyInfo;
import com.example.demo1.entity.Stock;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class FundHeavyDaoImpl implements FundHeavyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StockDaoImpl stockDaoImpl;
    private List<FundHeavy> fundHeavyAll;
    private List<FundHeavy> fundHeavyAllTemp;



//    private List<FundHeavy> globalFundHeavy;
    @Override
    public List<FundHeavy> getListAll() {
        if(fundHeavyAll==null){
            setFundHeavyAll();
            //preprocess();
        }
        //FundHeavy fundHeavy=fundHeavyAll.get(0);
        //if(fundHeavy.stock_type.isEmpty());
        ////System.out.println(111);
        //preprocess();
        return fundHeavyAll;
    }

    public void setFundHeavyAll() {
        String sql = "select * from m_fund_heavy ";//where id  =?or id =?or id =?or id =?

//        String sql2 =
//        "SELECT * FROM m_fund_heavy INNER JOIN m_stock ON m_fund_heavy.stock_id_1=m_stock.id";
        String sql2="SELECT * from(SELECT F.id as id , F.name as name , S1.type as stock_type_1 ,S1.id as stock_id_1 ,  F.stock_ratio_1 FROM m_fund_heavy as F INNER JOIN m_stock as S1 ON F.stock_id_1=S1.id order by id)as T1 natural join(SELECT F.id as id , S2.type as stock_type_2 , S2.id as stock_id_2 , F.stock_ratio_2 FROM m_fund_heavy as F INNER JOIN m_stock as S2 ON F.stock_id_2=S2.id order by id)as T2 natural join(SELECT F.id as id , S3.type as stock_type_3 , S3.id as stock_id_3 ,  F.stock_ratio_3 FROM m_fund_heavy as F INNER JOIN m_stock as S3 ON F.stock_id_3=S3.id order by id)as T3 natural join(SELECT F.id as id , S4.type as stock_type_4 , S4.id as stock_id_4 ,  F.stock_ratio_4 FROM m_fund_heavy as F INNER JOIN m_stock as S4 ON F.stock_id_4=S4.id order by id)as T4 natural join(SELECT F.id as id , S5.type as stock_type_5 , S5.id as stock_id_5 ,  F.stock_ratio_5 FROM m_fund_heavy as F INNER JOIN m_stock as S5 ON F.stock_id_5=S5.id order by id)as T5 order by id";
        String sql3="SELECT * from(SELECT F.id as id , S6.type as stock_type_6 , S6.id as stock_id_6 , F.stock_ratio_6 FROM m_fund_heavy as F INNER JOIN m_stock as S6 ON F.stock_id_6=S6.id order by id)as T6 natural join(SELECT F.id as id , S7.type as stock_type_7 , S7.id as stock_id_7 , F.stock_ratio_7 FROM m_fund_heavy as F INNER JOIN m_stock as S7 ON F.stock_id_7=S7.id order by id)as T7 natural join(SELECT F.id as id , S8.type as stock_type_8 , S8.id as stock_id_8 ,  F.stock_ratio_8 FROM m_fund_heavy as F INNER JOIN m_stock as S8 ON F.stock_id_8=S8.id order by id)as T8 natural join(SELECT F.id as id , S9.type as stock_type_9 , S9.id as stock_id_9 ,  F.stock_ratio_9 FROM m_fund_heavy as F INNER JOIN m_stock as S9 ON F.stock_id_9=S9.id order by id)as T9  natural join(SELECT F.id as id ,S10.type as stock_type_10 , S10.id as stock_id_10 ,  F.stock_ratio_10 FROM m_fund_heavy as F INNER JOIN m_stock as S10 ON F.stock_id_10=S10.id order by id)as T10 order by id";


//        INNER JOIN table_name2
//        ON table_name1.column_name=table_name2.column_name

        String sql1 = "update * from m_fund_heavy inner m_stock on m_fund_heavy.stock_id_1=m_stock.id set m_stock.stock_type_1=m_stock.type ";
        //select * from d_menu where name like concat('%',?,'%')or id =?,s2or id =?
//        String s1 = "000001.OF";
//        String s2 = "000309.OF";
//        String s3 = "000513.OF";
//        String s4 = "000893.OF";


//        this.jdbcTemplate.update(
//                sql1
//
//        );
        //////////////////////////////////////////////1-5
        fundHeavyAll = jdbcTemplate.query(sql2, new RowMapper<FundHeavy>() {
            @Override
            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                FundHeavy fundHeavy = new FundHeavy();
                fundHeavy.setId(resultSet.getString("id"));
                fundHeavy.setName(resultSet.getString("name"));
                //fundHeavy.set_stock_id_1(resultSet.getString("stock_id_1"));
                //fundHeavy.set_stock_name_1(resultSet.getString("id"))
                //fundHeavy.set_stock_id_1(resultSet.getString("id"));
                for (int k = 1; k <= 5; k++) {
                    fundHeavy.set_stock_id(k - 1, resultSet.getString("stock_id_" + k));
                    ////System.out.println( fundHeavy.getStock_id()[k-1]);;
                    //这里存名字感觉没什么必要，数据库要不要删名字列有待讨论,删名字列能减少m_fund_heavy的字段数量，当时增加查询的次数
                    fundHeavy.set_Stock_all_type(k - 1, resultSet.getString("stock_type_" + k));
                    String temp=fundHeavy.get_stock_id()[k-1];
                    ////System.out.println(fundHeavy.getStock_id()[k-1]);
                    fundHeavy.set_stock_ratio(k - 1, resultSet.getString("stock_ratio_" + k));
                    Stock stock=null;//resultSet.getString("stock_id_" + 1)
                    ////System.out.println(43434);
                    ////System.out.println(444444434);
//                    if(k==10){
//                        stock= stockDaoImpl.getById(resultSet.getString("stock_id_" + k));
//                    }
//                    if(stock==null);
//                    else
//                    fundHeavy.set_stock_type(stockDaoImpl.getById(resultSet.getString("stock_id_" + k)).getType());
                }
//                //System.out.println(43434);
//                Stock stock=null;
//                stock=stockDaoImpl.getById("000799.SZ");
                return fundHeavy;
            }
        });
        ///////////////////////////////////////////////6-10
        fundHeavyAllTemp = jdbcTemplate.query(sql3, new RowMapper<FundHeavy>() {
            @Override
            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                FundHeavy fundHeavy = new FundHeavy();
                fundHeavy.setId(resultSet.getString("id"));
                //fundHeavy.set_name(resultSet.getString("name"));
                //fundHeavy.set_stock_id_1(resultSet.getString("stock_id_1"));
                //fundHeavy.set_stock_name_1(resultSet.getString("id"))
                //fundHeavy.set_stock_id_1(resultSet.getString("id"));
                for (int k = 6; k <= 10; k++) {
                    fundHeavy.set_stock_id(k - 1, resultSet.getString("stock_id_" + k));
                    ////System.out.println( fundHeavy.getStock_id()[k-1]);;
                    //这里存名字感觉没什么必要，数据库要不要删名字列有待讨论,删名字列能减少m_fund_heavy的字段数量，当时增加查询的次数
                    fundHeavy.set_Stock_all_type(k - 1, resultSet.getString("stock_type_" + k));
                    String temp=fundHeavy.get_stock_id()[k-1];
                    ////System.out.println(fundHeavy.getStock_id()[k-1]);
                    fundHeavy.set_stock_ratio(k - 1, resultSet.getString("stock_ratio_" + k));
                    Stock stock=null;//resultSet.getString("stock_id_" + 1)
                    ////System.out.println(43434);
                    ////System.out.println(444444434);
//                    if(k==10){
//                        stock= stockDaoImpl.getById(resultSet.getString("stock_id_" + k));
//                    }
//                    if(stock==null);
//                    else
//                    fundHeavy.set_stock_type(stockDaoImpl.getById(resultSet.getString("stock_id_" + k)).getType());
                }
//                //System.out.println(43434);
//                Stock stock=null;
//                stock=stockDaoImpl.getById("000799.SZ");
                return fundHeavy;
            }
        });

        //整合
        int size= fundHeavyAll.size();
        int sizeT= fundHeavyAllTemp.size();
        for(int i=0;i<size;i++) {
            FundHeavy fundHeavy = fundHeavyAll.get(i);
            ////System.out.println(43434);
            for(int j=0;j<sizeT;j++) {

                FundHeavy t = fundHeavyAllTemp.get(j);
                if(Objects.equals(fundHeavy.id, t.id)){
                    for (int k = 6; k <= 10; k++) {
                        fundHeavy.set_stock_id(k - 1, t.get_stock_id()[k - 1]);

                        fundHeavy.set_Stock_all_type(k - 1, t.get_stock_all_Type()[k - 1]);
                        String temp = fundHeavy.get_stock_id()[k - 1];


                        fundHeavy.set_stock_ratio(k - 1, t.get_stock_ratio()[k - 1]);

                    }
                    break;
                }
                else continue;

            }
        }
    }

    public void preprocess() {
        int size= fundHeavyAll.size();

        /*//
        //
         */
       

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<10;j++)
            {
                FundHeavy fundHeavy=fundHeavyAll.get(i);
                String temp= fundHeavy.get_stock_id()[j];
//                //System.out.println(fundHeavy.id);
//                //System.out.println(temp);
//                //System.out.println(stockDaoImpl.getById(temp));
                //if(fundHeavy.stock_type.contains(temp))continue;
                Stock asd=stockDaoImpl.getById(temp);
                if(asd==null)continue;
//                //System.out.println(stockDaoImpl.getById(temp).getType());
//                //System.out.println(stockDaoImpl.getById(temp).getType());
//                //System.out.println(stockDaoImpl.getById(temp).getType());
                //fundHeavy.stock_type.contains(stock_type);
                //fundHeavy.set_stock_type(asd.getType());
                ////System.out.println(1);
            }
        }


    }

    @Override
    public List<String> getIdListByCompany(String company) {
        String sql="select id from m_fund_info where legal_person = ?";
        List<String> ans=null;
        try {
            ans=this.jdbcTemplate.query(sql, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int i) throws SQLException {
                    String id=resultSet.getString("id");
                    return id;
                }
            },company);
        }
        catch(DataAccessException e){
            ans=null;
        }
        return ans;
    }

    @Override
    public List<FundHeavyInfo> getFundHeavyInfoByNotId(String str) {
        String sql="select * from m_fund_info where name LIKE concat('%',?,'%') or legal_person LIKE concat('%',?,'%')" +
                " or full_name LIKE concat('%',?,'%') or manager LIKE concat('%',?,'%')";
        List<FundHeavyInfo> ans=null;
        try {
            ans=this.jdbcTemplate.query(sql, new RowMapper<FundHeavyInfo>() {
                @Override
                public FundHeavyInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                    FundHeavyInfo fundHeavyInfo=new FundHeavyInfo();
                    fundHeavyInfo.setId(resultSet.getString("id"));
                    fundHeavyInfo.setName(resultSet.getString("name"));
                    fundHeavyInfo.setFull_name(resultSet.getString("full_name"));
                    fundHeavyInfo.setLegal_person(resultSet.getString("legal_person"));
                    fundHeavyInfo.setManager(resultSet.getString("manager"));
                    return fundHeavyInfo;
                }
            },str,str,str,str);
        }
        catch(DataAccessException e){
            ans=null;
        }
        return ans;
    }

    @Override
    public List<FundHeavyInfo> getFundHeavyInfoById(String id) {
        String sql="select * from m_fund_info where id = ?";
        FundHeavyInfo result = null;
        try{
            result=this.jdbcTemplate.queryForObject(sql, new RowMapper<FundHeavyInfo>() {
                @Override
                public FundHeavyInfo mapRow(ResultSet resultSet, int i) throws SQLException {
                    FundHeavyInfo fundHeavyInfo=new FundHeavyInfo();
                    fundHeavyInfo.setId(resultSet.getString("id"));
                    fundHeavyInfo.setName(resultSet.getString("name"));
                    fundHeavyInfo.setFull_name(resultSet.getString("full_name"));
                    fundHeavyInfo.setLegal_person(resultSet.getString("legal_person"));
                    fundHeavyInfo.setManager(resultSet.getString("manager"));
                    return fundHeavyInfo;
                }
            },id);
        } catch(DataAccessException e){
            ////System.out.println(5555);
            result=null;
        }
        List<FundHeavyInfo> ans=new ArrayList<FundHeavyInfo>();
        ans.add(result);
        return ans;
    }

    @Override
    public List<FundHeavy> getFundHeavyById(String id) {
        String sql="select * from m_fund_heavy where id = ?";
        FundHeavy result = null;
        try{
            result=this.jdbcTemplate.queryForObject(sql, new RowMapper<FundHeavy>() {
                @Override
                public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                    FundHeavy fundHeavy=new FundHeavy();
                    fundHeavy.setId(resultSet.getString("id"));
                    fundHeavy.setName(resultSet.getString("name"));

                    return fundHeavy;
                }
            },id);
        } catch(DataAccessException e){
            ////System.out.println(5555);
            result=null;
        }
        List<FundHeavy> ans=new ArrayList<FundHeavy>();
        ans.add(result);
        return ans;
    }


    //        String sql = "select * from m_fund_heavy ";//where id  =?or id =?or id =?or id =?
//        //select * from d_menu where name like concat('%',?,'%')or id =?,s2or id =?
//        String s1="000001.OF";String s2="000309.OF";String s3="000513.OF";String s4="000893.OF";
//        List<FundHeavy> fundHeavy1 = jdbcTemplate.query(sql, new RowMapper<FundHeavy>() {
//            @Override
//            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
//                FundHeavy fundHeavy = new FundHeavy();
//                fundHeavy.setId(resultSet.getString("id"));
//                fundHeavy.setName(resultSet.getString("name"));
//                //fundHeavy.set_stock_id_1(resultSet.getString("stock_id_1"));
//                //fundHeavy.set_stock_name_1(resultSet.getString("id"))
//                //fundHeavy.set_stock_id_1(resultSet.getString("id"));
//                for(int k=1;k<=10;k++){
//                    fundHeavy.set_stock_id(k-1,resultSet.getString("stock_id_"+k));
//                    fundHeavy.set_stock_name(k-1,resultSet.getString("stock_name_"+k));
//                    fundHeavy.set_stock_ratio(k-1,resultSet.getString("stock_ratio_"+k));
//                }
//              fundHeavy.set_stock_id(
//                        resultSet.getString("stock_id_1"),
//                        resultSet.getString("stock_id_2"),
//                        resultSet.getString("stock_id_3"),
//                        resultSet.getString("stock_id_4"),
//                        resultSet.getString("stock_id_5"),
//                        resultSet.getString("stock_id_6"),
//                        resultSet.getString("stock_id_7"),
//                        resultSet.getString("stock_id_8"),
//                        resultSet.getString("stock_id_9"),
//                        resultSet.getString("stock_id_10"));
//                fundHeavy.set_stock_name(
//                        resultSet.getString("stock_name_1"),
//                        //"s","d","","","","","","","");
//                        resultSet.getString("stock_name_2"),
//                        resultSet.getString("stock_name_3"),
//                        resultSet.getString("stock_name_4"),
//                        resultSet.getString("stock_name_5"),
//                        resultSet.getString("stock_name_6"),
//                        resultSet.getString("stock_name_7"),
//                        resultSet.getString("stock_name_8"),
//                        resultSet.getString("stock_name_9"),
//                        resultSet.getString("stock_name_10"));
//                fundHeavy.set_stock_ratio(
//                        resultSet.getString("stock_ratio_1"),
//                        //"s","d","","","","","","","");
//                        resultSet.getString("stock_ratio_2"),
//                        resultSet.getString("stock_ratio_3"),
//                        resultSet.getString("stock_ratio_4"),
//                        resultSet.getString("stock_ratio_5"),
//                        resultSet.getString("stock_ratio_6"),
//                        resultSet.getString("stock_ratio_7"),
//                        resultSet.getString("stock_ratio_8"),
//                        resultSet.getString("stock_ratio_9"),
//                        resultSet.getString("stock_ratio_10"));
                //User user = new User(id, name, psw);
//                return fundHeavy;
//            }
//        });//,s1,s2,s3,s4
//        return fundHeavy1;
//    @Override
//    public List<FundHeavy> getById(String id) {
//        String sql="select * from m_fund_heavy where id = ?";
//        FundHeavyInfo result = null;
//        try{
//            result=this.jdbcTemplate.queryForObject(sql, new RowMapper<FundHeavy>() {
//                @Override
//                public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
//                    FundHeavy fundHeavy=new FundHeavyInfo();
//                    fundHeavy.setId(resultSet.getString("id"));
//                    fundHeavy.setName(resultSet.getString("name"));
//                    fundHeavyInfo.setFull_name(resultSet.getString("full_name"));
//                    fundHeavyInfo.setLegal_person(resultSet.getString("legal_person"));
//                    fundHeavyInfo.setManager(resultSet.getString("manager"));
//                    return fundHeavyInfo;
//                }
//            },id);
//        } catch(DataAccessException e){
//            ////System.out.println(5555);
//            result=null;
//        }
//        List<FundHeavy> ans=new ArrayList<FundHeavyInfo>();
//        ans.add(result);
//        return ans;
//    }

}



    //测试用
//    @Override
//    public FundHeavy getFundHeavy() {
//        String sql = "select * from m_fund_heavy where id =? ";
//        String s1="000001.OF";String s2="000309.OF";
//        String s3="000513.OF";String s4="000893.OF";//or id =?or id =?,s3,s4or id =?,s2
//
//        return this.jdbcTemplate.queryForObject(sql, new RowMapper<FundHeavy>() {
//            @Override
//            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
//                FundHeavy fundHeavy = new FundHeavy();
//                fundHeavy.setId(resultSet.getString("id"));
////                user.setUsername(resultSet.getString("username"));
////                user.setPassword(resultSet.getString("password"));
//                return fundHeavy;
//            }
//        },s1);
//    }
