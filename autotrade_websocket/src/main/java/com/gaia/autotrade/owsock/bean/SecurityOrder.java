package com.gaia.autotrade.owsock.bean;


//****************************************************************************\
//*                                                                             *
//* SecurityServiceEx.cs - Security service functions, types, and definitions.  *
//*                                                                             *
//*               Version 1.00  ★★★                                          *
//*                                                                             *
//*               Copyright (c) 2016-2016, Piratecat. All rights reserved.      *
//*               Created by QiChunyou 2016/6/6.                                *
//*                                                                             *
//*****************************************************************************


/**
 股票权值、信息

 */
public class SecurityOrder
{
    /**
     创建权值信息

     */
    public SecurityOrder()
    {
    }

    /**
     权值

     */
    public int m_order;

    /**
     键盘精灵

     */
    public SecurityInfo m_security;
}