import React, { Component } from 'react';
import CommonHeader from '../../header/CommonHeader';
import { withRouter } from 'react-router-dom';
import './dashboard.css';

import Sidebar from "./Sidebar";

class Dashboard extends Component {

    render() {
        return (
            <div className="container-body admin-dashboard" id="outer-container">
                <div className="admin">
                    <CommonHeader />
                </div>

                <div id="wrapper" className="toggled">
                    <div id="sidebar-wrapper">
                        <Sidebar />
                    </div>
                    <div id="page-content-wrapper">
                        <div className="container-fluid">
                            <h1>Admin Dashboard</h1>
                        </div>
                    </div>
                </div>
            </div>

        )
    }
}

export default withRouter(Dashboard);