#ifndef CITY_HPP
#define CITY_HPP

#include <string>
#include <unordered_map>

#include "country.hpp"

class City {
private:

    std::string code, name;
    double longitude, lattitude;
    Country::ref_t country;

public:

    using ref_t = std::shared_ptr<City>;

    class Registry {
    private:

        std::unordered_map<std::string, ref_t> cities;

        static Registry &get_instance();

        void add_impl(const std::string &code, const std::string &name, const double longitude, const double lattitude, const Country::ref_t country);
        ref_t get_impl(const std::string &code);

    public:

        static void add(const std::string &code, const std::string &name, const double longitude, const double lattitude, const Country::ref_t country);
        static ref_t get(const std::string &name);

    };

    static ref_t create(const std::string &code, const std::string &name, const double longitude, const double lattitude, const Country::ref_t country);

};

#endif // CITY_HPP