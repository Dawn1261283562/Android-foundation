package com.example.demo1.dao.impl;

import com.example.demo1.dao.FundHeavyDao;
import com.example.demo1.entity.FundHeavy;
import com.example.demo1.entity.Stock;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class FundHeavyDaoImpl implements FundHeavyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StockDaoImpl stockDaoImpl;
    private List<FundHeavy> fundHeavyAll;

//    private List<FundHeavy> globalFundHeavy;
    @Override
    public List<FundHeavy> getListAll() {
        if(fundHeavyAll==null){
            setFundHeavyAll();
            //preprocess();
        }
        //FundHeavy fundHeavy=fundHeavyAll.get(0);
        //if(fundHeavy.stock_type.isEmpty());
        //System.out.println(111);
        //preprocess();
        return fundHeavyAll;
    }

    public void setFundHeavyAll() {
        String sql = "select * from m_fund_heavy ";//where id  =?or id =?or id =?or id =?

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
        fundHeavyAll = jdbcTemplate.query(sql, new RowMapper<FundHeavy>() {
            @Override
            public FundHeavy mapRow(ResultSet resultSet, int i) throws SQLException {
                FundHeavy fundHeavy = new FundHeavy();
                fundHeavy.set_id(resultSet.getString("id"));
                fundHeavy.set_name(resultSet.getString("name"));
                //fundHeavy.set_stock_id_1(resultSet.getString("stock_id_1"));
                //fundHeavy.set_stock_name_1(resultSet.getString("id"))
                //fundHeavy.set_stock_id_1(resultSet.getString("id"));
                for (int k = 1; k <= 10; k++) {
                    fundHeavy.set_stock_id(k - 1, resultSet.getString("stock_id_" + k));
                    //System.out.println( fundHeavy.getStock_id()[k-1]);;
                    //这里存名字感觉没什么必要，数据库要不要删名字列有待讨论,删名字列能减少m_fund_heavy的字段数量，当时增加查询的次数
                    fundHeavy.set_Stock_all_type(k - 1, resultSet.getString("stock_type_" + k));
                    String temp=fundHeavy.get_stock_id()[k-1];
                    //System.out.println(fundHeavy.getStock_id()[k-1]);
                    fundHeavy.set_stock_ratio(k - 1, resultSet.getString("stock_ratio_" + k));
                    Stock stock=null;//resultSet.getString("stock_id_" + 1)
                    //System.out.println(43434);
                    //System.out.println(444444434);
//                    if(k==10){
//                        stock= stockDaoImpl.getById(resultSet.getString("stock_id_" + k));
//                    }
//                    if(stock==null);
//                    else
//                    fundHeavy.set_stock_type(stockDaoImpl.getById(resultSet.getString("stock_id_" + k)).getType());
                }
//                System.out.println(43434);
//                Stock stock=null;
//                stock=stockDaoImpl.getById("000799.SZ");
                return fundHeavy;
            }
        });
    }

    public void preprocess() {
        int size= fundHeavyAll.size();

        for(int i=0;i<size;i++)
        {
            for(int j=0;j<10;j++)
            {
                FundHeavy fundHeavy=fundHeavyAll.get(i);
                String temp= fundHeavy.get_stock_id()[j];
//                System.out.println(fundHeavy.id);
//                System.out.println(temp);
//                System.out.println(stockDaoImpl.getById(temp));
                //if(fundHeavy.stock_type.contains(temp))continue;
                Stock asd=stockDaoImpl.getById(temp);
                if(asd==null)continue;
//                System.out.println(stockDaoImpl.getById(temp).getType());
//                System.out.println(stockDaoImpl.getById(temp).getType());
//                System.out.println(stockDaoImpl.getById(temp).getType());
                //fundHeavy.stock_type.contains(stock_type);
                fundHeavy.set_stock_type(asd.getType());
                //System.out.println(1);
            }
        }


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
