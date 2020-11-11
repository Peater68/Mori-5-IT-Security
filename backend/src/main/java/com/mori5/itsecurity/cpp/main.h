#ifndef main_h
#define main_h

using namespace::std;

class Pixel {
public:
    uint8_t red;
    uint8_t green;
    uint8_t blue;
};

class Image {
public:
    vector<Pixel> pixels;
    uint64_t content_size;
    uint64_t width;
    uint64_t height;
    uint64_t duration;
    string tags;
    string caption;
    Image(vector<Pixel> &pixels, uint64_t content_size, uint64_t width, uint64_t height, uint64_t duration, string tags, string caption);
};

class CreatorsImages {
public:
    uint16_t year;
    uint8_t month;
    uint8_t day;
    uint8_t hour;
    uint8_t minute;
    uint64_t lengthOfCreator;
    string creatorString;
    vector<Image> images;
    string error;
    CreatorsImages(uint16_t year, uint8_t month, uint8_t day, uint8_t hour, uint8_t minute, uint64_t lengthOfCreator, string creatorString, vector<Image> &images);
    CreatorsImages();
};

namespace CaffReader
{
   CreatorsImages readCaffFromFile(const string &filename);
}

#endif