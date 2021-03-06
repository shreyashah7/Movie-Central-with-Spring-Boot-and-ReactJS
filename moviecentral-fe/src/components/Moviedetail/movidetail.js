import React, { Component } from 'react';
import HomeHeader from './../header/CommonHeader'
import './moviedetail.css'
import MoveOverview from './MovieOverview/MovieOverview'
import { connect } from "react-redux";
import {bindActionCreators} from "redux";
import MovieCrew from './MovieCrew/MovieCrew'
import MovieReview from "./MovieOverview/MovieReview";
import AddReview from "./MovieOverview/MovieAddReview";

class Movie_detail extends Component {

    constructor(props) {
        super(props);
        this.state = {
            ActiveComponent: <MoveOverview />,
            showSynopsys: 'block'
        }
    }

    handleCastClick = ()=> {
        this.setState({...this.state,ActiveComponent: <MovieCrew/>, showSynopsys: 'block'})
    };

    handleReviews = ()=>{
        this.setState({...this.state,ActiveComponent: <MovieReview onAdd={this.handleAddReviews}/>,showSynopsys:'none'})
    };

    handleAddReviews = ()=>{
        this.setState({...this.state, ActiveComponent: <AddReview onDone={this.handleReviews}/>})
    };

    render() {
        let background = "//images.fandango.com/ImageRenderer/300/0/redesign/static/img/default_poster.png/0/images/masterrepository/Fandango/207628/fmc_mc_Rampage.jpg";
        if (this.props.movie.image) background = this.props.movie.image;

        return (
            <div>
                <HomeHeader />
                <div className="movie-detail-main">
                    <div className="movie-detail-mop">
                        <div className="movie-detail-background">
                            <svg width="100%" height="100%">
                                <defs>
                                    <filter id="backgroundBlur" width="150%" height="150%" x="-25%" y="-25%"
                                            colorInterpolationFilters="sRGB">
                                        <feGaussianBlur stdDeviation="7"></feGaussianBlur>
                                        <feColorMatrix type="matrix"
                                            values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0 0 0 0 10 0"></feColorMatrix>
                                        <feComposite in2="SourceGraphic" operator="in"></feComposite>
                                    </filter>
                                </defs>
                                <image className="js-backgroundBlur-image" x="0" y="0" width="100%" height="110%"
                                    xlinkHref={background}
                                    preserveAspectRatio="xMidYMid slice" filter="url(#backgroundBlur)"></image>
                            </svg>
                        </div>
                        <div className="movie-detail-background-next">
                            <section className="movie-detail-subnav">
                                <div className="movie-detail-section-row">
                                    <div className="movie-detail-section-row-100">
                                        <h1 className="movie-detail-section-row-title">

                                            {this.props.movie.title}

                                        </h1>
                                        <ul className="movie-detail-section-subnav">
                                            <li className="movie-detail-section-subnav-item">
                                                <label className="movie-detail-section-subnav-item-link" onClick={() => this.setState({ ...this.state, ActiveComponent: <MoveOverview />,  showSynopsys: 'block' })}>
                                                    Overview
                                                </label>
                                            </li>
                                            <li className="movie-detail-section-subnav-item">
                                                <label className="movie-detail-section-subnav-item-link" onClick={()=>this.handleReviews()} >REVIEWS</label>

                                            </li>
                                            <li className="movie-detail-section-subnav-item">
                                                <label className="movie-detail-section-subnav-item-link" onClick={()=>this.handleCastClick()}>
                                                    CAST
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </section>
                            <div className="footer-overview" style={{'display':this.state.showSynopsys}}>
                                <h4 className="footer-overview-font">{this.props.movie.title}   <span style={{ color: "#f15500" }}>Synopsis</span></h4>
                                <div style={{ marginLeft: "25%", width: "50%", textAlign: "center", marginTop: '20px' }}>
                                    <h4 className="footer-overview-font" style={{ paddingTop: '0px' }}>{this.props.movie.synopsis} </h4>
                                </div>
                            </div>
                            {this.state.ActiveComponent}
                        </div>
                    </div>

                </div>

            </div>);
    };
}

function mapStateToProps(state) {
    return {
        user: state.loginUser,
        movie: state.selectedMovie
    }
}

function matchDispatchToProps(dispatch) {
    return bindActionCreators({}, dispatch)
}

export default connect(mapStateToProps,matchDispatchToProps)(Movie_detail);