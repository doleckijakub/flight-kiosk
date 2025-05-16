#ifndef COUNTRY_HPP
#define COUNTRY_HPP

#include <string>

class Country {
private:

    std::string name;

public:

    using ref_t = std::shared_ptr<Country>;

    class Registry {
    private:

        std::vector<ref_t> countries;

        static Registry &get_instance();

        void add_impl(const std::string &name);
        ref_t get_impl(const std::string &name);

    public:

        static void add(const std::string &name);
        static ref_t get(const std::string &name);

    };

    static ref_t create(const std::string &name);

};

#endif // COUNTRY_HPP