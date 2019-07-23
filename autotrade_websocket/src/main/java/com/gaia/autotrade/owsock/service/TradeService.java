package com.gaia.autotrade.owsock.service;

import java.io.IOException;
import java.util.ArrayList;

import com.gaia.autotrade.owsock.base.BaseService;
import com.gaia.autotrade.owsock.base.Binary;
import com.gaia.autotrade.owsock.base.CMessage;
import com.gaia.autotrade.owsock.global.BusinessIDs;
import com.gaia.autotrade.owsock.global.DataCenter;
import com.gaia.autotrade.owsock.manager.TradeDataManager;
import com.gaia.autotrade.owsock.trade_bean.CancelOrder;
import com.gaia.autotrade.owsock.trade_bean.CoinOrder;
import com.gaia.autotrade.owsock.trade_bean.CoinPosition;
import com.gaia.autotrade.owsock.trade_bean.CoinTrade;
import com.gaia.autotrade.owsock.trade_bean.DepositAddress;
import com.gaia.autotrade.owsock.trade_bean.WithdrawalApplyRecord;

public class TradeService extends BaseService {

    public static final int SERVICEID_TDSERVICE = 55;

    public TradeService() {
        SetServiceID(SERVICEID_TDSERVICE);
    }

    public TradeDataManager m_securityServiceEx;



    public int GenerateReqID() {
        int id = 1000;
        return id++;
    }


    //接收消息
    public void OnReceive(CMessage message) {
        super.OnReceive(message);
        Binary binary = new Binary();
        binary.Write(message.m_body, message.m_bodyLength);
        switch (message.m_functionID) {
            //登录回报
            case BusinessIDs.API_TD_LOGIN:
                OnRspLogin(binary);
                break;
            //登出回报
            case BusinessIDs.API_TD_LOGOUT:
                OnRspLogOut(binary);
                break;
            //资金回报
            case BusinessIDs.QRY_ALL_POSITIONS:
                OnRspPositions(binary);
                break;
            //Ginex资金的直接推送
            case BusinessIDs.API_TD_POSITION:
                OnRspPositions(binary);
                break;
            //资金变化推送
            case BusinessIDs.API_TD_NOTIFY_POSITION:
                OnRtnPosition(binary);
                break;
            case BusinessIDs.API_TD_DEPOSIT:
                OnRtnRechargeAddr(binary);
                break;
            //委托回报
            case BusinessIDs.API_TD_ORDER:
                OnRtnOrders(binary);
                break;
            //成交回报
            case BusinessIDs.API_TD_NOTIFY_ORDER_DEAL:
                OnRtnTrades(binary);
                break;
            //撤单回报
            case BusinessIDs.API_TD_CANCEL_ORDER:
                OnRtnCancelOrder(binary);
                break;
            //地址回报
            case BusinessIDs.QRY_RECHARGE_ADDR:
                OnRtnRechargeAddr(binary);
                break;
            //提现申请回报
            case BusinessIDs.API_TD_WITHDRAWAL:
                OnRtnWithdrawalApply(binary);
                break;
            //历史提现回报
            case BusinessIDs.QRY_ALL_WITHDRAWAL:
                OnRspHistroyWithdrawl(binary);
                break;
            //历史委托回报
            case BusinessIDs.QRY_ALL_ORDERS:
                OnRspHistoryOrders(binary);
                break;
            //历史成交回报
            case BusinessIDs.QRY_ALL_TRADES:
                OnRspHistroyTrades(binary);
                break;
            default:
                break;
        }

    }

    //登录回报
    private boolean OnRspLogin(Binary binary) {
        try {
            String flag  = binary.ReadString();
            String traderID = binary.ReadString();
            int errorCode = binary.ReadInt();
            ReqQryPositions(traderID);
            ReqHistoryOrders(traderID);
            ReqHistoryTrades(traderID);
            ReqHistoryWithdraws("flag", traderID);
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    //登出回报
    private boolean OnRspLogOut(Binary binary) {
        try {
            String flag  = binary.ReadString();
            String traderID = binary.ReadString();
            int errorCode = binary.ReadInt();
            return true;

        } catch (IOException e) {
            return false;
        }
    }

    //历史委托回报
    private void OnRspHistoryOrders(Binary binary) {
        try {
            String flag = binary.ReadString();
            String traderID = binary.ReadString();
            ArrayList<CoinOrder> vec = new ArrayList<CoinOrder>();
            ReadOrderInfos(binary, vec);
            m_securityServiceEx.OnOrders(vec);

        } catch (IOException e) {
        }
    }

    //资金回报
    private void OnRspPositions(Binary binary) {
        try {
            String flag = binary.ReadString();
            String traderID = binary.ReadString();
            ArrayList<CoinPosition> vec = new ArrayList<>();
            ReadPositions(binary, vec);
            m_securityServiceEx.OnPositions(vec);
        } catch (IOException e) {
        }
    }

    //历史成交回报
    private void OnRspHistroyTrades(Binary binary) {
        try {
            String traderID = binary.ReadString();
            ArrayList<CoinTrade> vec = new ArrayList<CoinTrade>();
            ReadTradeRecords(binary, vec);
            m_securityServiceEx.OnTrades(vec);
        } catch (IOException e) {
        }
    }

    //历史提现回报
    public void OnRspHistroyWithdrawl(Binary binary)
    {
        ArrayList<WithdrawalApplyRecord> records = new ArrayList<>();
        try
        {
            String traderID = binary.ReadString();
            int size = binary.ReadInt();
            for(int i = 0; i < size; i++)
            {
                WithdrawalApplyRecord record = new WithdrawalApplyRecord();
                record.m_flag = binary.ReadString();
                record.m_traderID = binary.ReadString();
                record.m_errorCode = binary.ReadInt();
                record.m_code = binary.ReadString();
                record.m_outAddr = binary.ReadString();
                record.m_memo = binary.ReadString();
                record.m_balance = binary.ReadDouble();
                record.m_fee = binary.ReadDouble();
                record.m_serialNumber = binary.ReadString();
                record.m_withdrawalDate = binary.ReadString();
                record.m_withdrawalTime = binary.ReadString();
                record.m_finishStatus = binary.ReadString();
                record.m_progress = binary.ReadDouble();
                records.add(record);
            }
        }
        catch ( Exception e)
        {}
        m_securityServiceEx.OnHistoryWithdraw(records);
    }

    //撤单回报
    public void OnRtnCancelOrder(Binary binary) {
        try
        {
            CancelOrder cancelOrder = new CancelOrder();
            cancelOrder.m_flag = binary.ReadString();
            cancelOrder.m_traderID = binary.ReadString();
            cancelOrder.m_errorCode = binary.ReadInt();
            cancelOrder.m_orderID = binary.ReadString();
            cancelOrder.m_tradePair = binary.ReadString();
            cancelOrder.m_price = binary.ReadDouble();
            cancelOrder.m_volume = binary.ReadDouble();
            cancelOrder.m_dir = binary.ReadInt();
            cancelOrder.m_cancelID = binary.ReadString();
            m_securityServiceEx.OnCancelOrderRecord(cancelOrder);
        }catch (IOException e){

        }
    }

    //请求地址回调
    public void OnRtnRechargeAddr(Binary binary)
    {
        try {
            DepositAddress address = new DepositAddress();
            address.m_flag = binary.ReadString();
            address.m_traderID = binary.ReadString();
            address.m_code = binary.ReadString();
            address.m_addr = binary.ReadString();
            address.m_memo = binary.ReadString();
            m_securityServiceEx.OnAddress(address);
            binary.Close();

        }
        catch (IOException e)
        {
        }
    }

    private void OnRtnWithdrawalApply(Binary binary) {
        WithdrawalApplyRecord withdrawalApplyRecord = new WithdrawalApplyRecord();
        try
        {
            withdrawalApplyRecord.m_flag = binary.ReadString();
            withdrawalApplyRecord.m_traderID = binary.ReadString();
            withdrawalApplyRecord.m_errorCode = binary.ReadInt();
            withdrawalApplyRecord.m_code = binary.ReadString();
            withdrawalApplyRecord.m_outAddr = binary.ReadString();
            withdrawalApplyRecord.m_memo = binary.ReadString();
            withdrawalApplyRecord.m_balance = binary.ReadDouble();
            withdrawalApplyRecord.m_fee = binary.ReadDouble();
            withdrawalApplyRecord.m_serialNumber = binary.ReadString();
            withdrawalApplyRecord.m_withdrawalDate = binary.ReadString();
            withdrawalApplyRecord.m_withdrawalTime = binary.ReadString();
            withdrawalApplyRecord.m_finishStatus = binary.ReadString();
            withdrawalApplyRecord.m_progress = binary.ReadDouble();
            m_securityServiceEx.OnWithdrawalApplyRecord(withdrawalApplyRecord);
        }catch(IOException e)
        {
        }
    }

    private void OnRtnOrders(Binary binary) {
        try {
            ArrayList<CoinOrder> vec = new ArrayList<CoinOrder>();
            CoinOrder order = new CoinOrder();
            ReadOrderInfo(binary, order);
            vec.add(order);
            m_securityServiceEx.OnOrders(vec);
        } catch (Exception e) {
        }
    }

    //资金变化推送
    private void OnRtnPosition(Binary binary) {
        try {
            String flag = binary.ReadString();
            String investorID = binary.ReadString();
            ArrayList<CoinPosition> vec = new ArrayList<>();
            CoinPosition position = new CoinPosition();
            ReadPosition(binary, position);
            vec.add(position);
            m_securityServiceEx.OnPositions(vec);
        } catch (IOException e) {
        }
    }

    private void OnRtnTrades(Binary binary) {
        try {
            String investorID = binary.ReadString();
            ArrayList<CoinTrade> vec = new ArrayList<>();
            ReadTradeRecords(binary, vec);
            m_securityServiceEx.OnTrades(vec);
        } catch (IOException e) {
        }
    }

    //买开，限价
    public int OpenBuy(String tradePair, String traderID, String feeTarget, String flag, int orderType, double price, double volume) {
        try {
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            binary.WriteString(tradePair);
            binary.WriteString(feeTarget);
            binary.WriteDouble(price);
            binary.WriteDouble(volume);
            binary.WriteInt(BusinessIDs.DIR_BUY);
            binary.WriteInt(orderType);
            int ret = RealSend(BusinessIDs.API_TD_ORDER, binary);
            binary.Close();
            return ret;
        } catch (IOException e) {
            return 0;
        }
    }

    //卖开，限价
    public int OpenSell(String tradePair, String traderID, String feeTarget, String flag, int orderType, double price, double volume) {
        try {
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            binary.WriteString(tradePair);
            binary.WriteString(feeTarget);
            binary.WriteDouble(price);
            binary.WriteDouble(volume);
            binary.WriteInt(BusinessIDs.DIR_SELL);
            binary.WriteInt(orderType);
            int ret = RealSend(BusinessIDs.API_TD_ORDER, binary);
            return ret;
        } catch (IOException e) {
            return 0;
        }
    }

    private void ReadOrderInfo(Binary binary, CoinOrder order) {
        try {
            order.m_flag = binary.ReadString();
            order.m_traderID = binary.ReadString();
            order.m_errorCode = binary.ReadInt();
            order.m_tradePair = binary.ReadString();
            order.m_orderID = binary.ReadString();
            order.m_targetFee = binary.ReadString();
            order.m_price = binary.ReadDouble();
            order.m_volume = binary.ReadDouble();
            order.m_orderFee = binary.ReadDouble();
            order.m_timestamp = binary.ReadDouble();
            order.m_isFeeTartget = binary.ReadInt();
            order.m_isDealFeeDemand = binary.ReadInt();
            order.m_orderWay = binary.ReadInt();
            order.m_dir = binary.ReadInt();
            order.m_tradedVolume = binary.ReadDouble();
            order.m_openedVolume = binary.ReadDouble();
            order.m_orderState = binary.ReadInt();
        } catch (IOException e) {
            System.out.println("委托信息读取出错!");
        }
    }

    private void ReadOrderInfos(Binary binary, ArrayList<CoinOrder> vec) {
        try {
            CoinOrder order;
            int size = binary.ReadInt();
            for (int i = 0; i < size; i++) {
                order = new CoinOrder();
                ReadOrderInfo(binary, order);
                vec.add(order);
            }
        } catch (IOException e) {
        }
    }

    private void ReadPositions(Binary binary, ArrayList<CoinPosition> vec) {
        try {
            CoinPosition position;
            int size = binary.ReadInt();
            for (int i = 0; i < size; i++) {
                position = new CoinPosition();
                position.m_code = binary.ReadString();
                position.m_volume = binary.ReadDouble();
                position.m_frozenVolume = binary.ReadDouble();
                vec.add(position);
            }
            binary.Close();
        } catch (IOException e) {
        }
    }

    private void ReadPosition(Binary binary, CoinPosition position)
    {
        try {
            position.m_code = binary.ReadString();
            position.m_volume = binary.ReadDouble();
            position.m_frozenVolume = binary.ReadDouble();
        } catch (IOException e) {
        }
    }

    private void ReadTradeRecord(Binary binary, CoinTrade record) {
        try {
            record.m_code = binary.ReadString();
            record.m_dealID = binary.ReadString();
            record.m_orderID = binary.ReadString();
            record.m_traderID = binary.ReadString();
            record.m_volume = binary.ReadDouble();
            record.m_price = binary.ReadDouble();
            record.m_timestamp = binary.ReadDouble();
            record.m_direction = binary.ReadInt();
            record.m_isFinish = binary.ReadInt();
        } catch (IOException e) {
        }
    }

    private void ReadTradeRecords(Binary binary, ArrayList<CoinTrade> vec) {
        try {
            CoinTrade record;
            int size = binary.ReadInt();
            for (int i = 0; i < size; i++) {
                record = new CoinTrade();
                ReadTradeRecord(binary, record);
                vec.add(record);
            }
            binary.Close();
        } catch (IOException e) {
        }
    }

    private int RealSend(int functionID, Binary binary) {
        byte[] ps = binary.GetBytes();
        CMessage message = new CMessage(GetGroupID(), GetServiceID(), functionID, GetSessionID(), GenerateReqID(), GetSocketID(), 0, GetCompressType(), ps.length, ps);
        return Send(message);
    }

    //充值地址请求
    public int ReqRechargeAddr(String flag, String traderID, String code)
    {
        try {
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            binary.WriteString(code);
            return RealSend(BusinessIDs.QRY_RECHARGE_ADDR, binary);
        }catch (IOException e){
            return 0;
        }
    }

    //历史提现请求
    public int ReqHistoryWithdraws(String flag, String traderID)
    {
        try {
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            return RealSend(BusinessIDs.QRY_ALL_WITHDRAWAL, binary);
        } catch (IOException e) {
            return 0;
        }
    }

    //提现请求
    public int ReqWithdrawal(String flag, String traderID, String code, String outAddr, String memo, double balance)
    {
        try {
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            binary.WriteString(code);
            binary.WriteString(outAddr);
            binary.WriteString(memo);
            binary.WriteDouble(balance);
            return RealSend(BusinessIDs.API_TD_WITHDRAWAL, binary);
        }catch (IOException e){
            return 0;
        }
    }

    //登录
    public int ReqLogin(String traderID, String password)
    {
        try {
            String flag = "broker";
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            binary.WriteString(password);
            return RealSend(BusinessIDs.API_TD_LOGIN, binary);
        }catch (IOException e){
            return 0;
        }
    }

    //登出请求
    public int ReqLogOut(String traderID)
    {
        try {
            String flag = "";
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            return RealSend(BusinessIDs.API_TD_LOGOUT, binary);
        }catch (IOException e){
            return 0;
        }
    }

    //请求撤单
    public void ReqCancelOrder(String traderID, String flag, String orderID)
    {
        try {
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            binary.WriteString(orderID);
            RealSend(BusinessIDs.API_TD_CANCEL_ORDER, binary);
        }catch (IOException e){

        }
    }

    //资金请求
    public void ReqQryPositions(String traderID)
    {
        try {
            String flag = "";
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            RealSend(BusinessIDs.QRY_ALL_POSITIONS, binary);
        }catch (IOException e){

        }
    }

    //历史委托请求
    public void ReqHistoryOrders(String traderID)
    {
        try {
            String flag = "";
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            binary.WriteInt(1000);
            int ret = RealSend(BusinessIDs.QRY_ALL_ORDERS, binary);
            int i = 0;
        }catch (IOException e){

        }
    }

    //历史成交请求
    public void ReqHistoryTrades(String traderID)
    {
        try {
            String flag = "";
            Binary binary = new Binary();
            binary.WriteString(flag);
            binary.WriteString(traderID);
            binary.WriteInt(1000);
            RealSend(BusinessIDs.QRY_ALL_TRADES, binary);
        }catch (IOException e){

        }
    }
}
