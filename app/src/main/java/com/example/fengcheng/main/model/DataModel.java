package com.example.fengcheng.main.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Package com.example.fengcheng.main.bbvaassignment
 * @FileName DataModel
 * @Date 4/22/18, 6:19 PM
 * @Author Created by fengchengding
 * @Description BBVAAssignment
 */

public class DataModel implements Serializable {

    private String status;
    private List<String> html_attributions;
    private List<ResultsBean> results;

    public DataModel(String status, List<String> html_attributions, List<ResultsBean> results) {
        this.status = status;
        this.html_attributions = html_attributions;
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * formatted_address : 311 S Wacker Dr #2590, Chicago, IL 60606美国
         * geometry : {"location":{"lat":41.877458,"lng":-87.63596299999999},"viewport":{"northeast":{"lat":41.87880152989272,"lng":-87.63495557010727},"southwest":{"lat":41.87610187010728,"lng":-87.63765522989272}}}
         * icon : https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png
         * id : 6db3ed9177927a6350fe5f7ad0685ea2c9094bb8
         * name : BBVA Compass
         * place_id : ChIJ2flErb8sDogRVHcsQoP_Scg
         * reference : CmRbAAAAFJ2akCQ1vvEsSX-IxcLsv4TCvf0wV05Dnf033QhohZG-Uq7ckzSSXqgqv5g5QE_Cx3hkXmObGnNYz_NzOMQH_lKXgC-vB_8iaf9v4tNj8SZ-XNGQFZca1-67TZh6Y6x6EhDMEsh7I0IwDZ7hOxjtMxczGhTJ3CQtbQ9oOi6-e6RuqiJbgGTFpw
         * types : ["finance","point_of_interest","establishment"]
         * opening_hours : {"open_now":true,"weekday_text":[]}
         * permanently_closed : true
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String icon;
        private String id;
        private String name;
        private String place_id;
        private String reference;
        private OpeningHoursBean opening_hours;
        private boolean permanently_closed;
        private List<String> types;

        public ResultsBean(String formatted_address, GeometryBean geometry, String icon, String id, String name, String place_id, String reference, OpeningHoursBean opening_hours, boolean permanently_closed, List<String> types) {
            this.formatted_address = formatted_address;
            this.geometry = geometry;
            this.icon = icon;
            this.id = id;
            this.name = name;
            this.place_id = place_id;
            this.reference = reference;
            this.opening_hours = opening_hours;
            this.permanently_closed = permanently_closed;
            this.types = types;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public OpeningHoursBean getOpening_hours() {
            return opening_hours;
        }

        public void setOpening_hours(OpeningHoursBean opening_hours) {
            this.opening_hours = opening_hours;
        }

        public boolean isPermanently_closed() {
            return permanently_closed;
        }

        public void setPermanently_closed(boolean permanently_closed) {
            this.permanently_closed = permanently_closed;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * location : {"lat":41.877458,"lng":-87.63596299999999}
             * viewport : {"northeast":{"lat":41.87880152989272,"lng":-87.63495557010727},"southwest":{"lat":41.87610187010728,"lng":-87.63765522989272}}
             */

            private LocationBean location;
            private ViewportBean viewport;

            public GeometryBean(LocationBean location, ViewportBean viewport) {
                this.location = location;
                this.viewport = viewport;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class LocationBean {
                /**
                 * lat : 41.877458
                 * lng : -87.63596299999999
                 */

                private double lat;
                private double lng;

                public LocationBean(double lat, double lng) {
                    this.lat = lat;
                    this.lng = lng;
                }

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }

                /**
                 * @param lastLatitude  current lat
                 * @param lastLongitude current longt
                 * calculate distance from current location to given location
                 */

                public Double countDistance(Double lastLatitude, Double lastLongitude){
                    Double distance = Math.pow((lastLongitude - Double.valueOf(lng)), 2) + Math.pow((lastLatitude - lat), 2);
                    return Math.sqrt(distance);
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":41.87880152989272,"lng":-87.63495557010727}
                 * southwest : {"lat":41.87610187010728,"lng":-87.63765522989272}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public ViewportBean(NortheastBean northeast, SouthwestBean southwest) {
                    this.northeast = northeast;
                    this.southwest = southwest;
                }

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 41.87880152989272
                     * lng : -87.63495557010727
                     */

                    private double lat;
                    private double lng;

                    public NortheastBean(double lat, double lng) {
                        this.lat = lat;
                        this.lng = lng;
                    }

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 41.87610187010728
                     * lng : -87.63765522989272
                     */

                    private double lat;
                    private double lng;

                    public SouthwestBean(double lat, double lng) {
                        this.lat = lat;
                        this.lng = lng;
                    }

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class OpeningHoursBean {
            /**
             * open_now : true
             * weekday_text : []
             */

            private boolean open_now;
            private List<String> weekday_text;

            public OpeningHoursBean(boolean open_now, List<String> weekday_text) {
                this.open_now = open_now;
                this.weekday_text = weekday_text;
            }


            public boolean isOpen_now() {
                return open_now;
            }

            public void setOpen_now(boolean open_now) {
                this.open_now = open_now;
            }

            public List<String> getWeekday_text() {
                return weekday_text;
            }

            public void setWeekday_text(List<String> weekday_text) {
                this.weekday_text = weekday_text;
            }
        }
    }
}
