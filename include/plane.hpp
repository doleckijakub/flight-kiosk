#ifndef PLANE_HPP
#define PLANE_HPP

#include <unordered_map>

class Plane {
public:

    class Model {
    private:

        const std::string number, name;
        const int rows1, rows2; // rows before middle exit, rows after middle exit
        const int col_groups, cols; // # seat groups in a row, # seats in a group

    public:

        using ref_t = std::shared_ptr<Model>;

    };

    class ModelRegistry {
    private:

        std::unordered_map<std::string, Model::ref_t> model;

        static ModelRegistry &get_instance();

        void add_impl(const std::string &number, const std::string &name, const int rows1, const int rows2, const int col_groups, const int cols);
        Model::ref_t get_impl(const std::string &code);

    public:

        static void add(const std::string &number, const std::string &name, const int rows1, const int rows2, const int col_groups, const int cols);
        static Model::ref_t get(const std::string &name);

    };

private:

    Model::ref_t model;

};

#endif // PLANE_HPP