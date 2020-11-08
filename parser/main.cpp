#include <iostream>
#include <fstream>
#include <list>
#include "vector"

#include "main.h"
#include "EasyBMP.hpp"

using namespace std;

Image::Image(vector<Pixel> &pixels, uint64_t content_size, uint64_t width, uint64_t height, uint64_t duration, string tags, string caption)
{
    this->pixels = pixels;
    this->content_size = content_size;
    this->width = width;
    this->height = height;
    this->duration = duration;
    this->tags = tags;
    this->caption = caption;
}

CreatorsImages::CreatorsImages(uint16_t year, uint8_t month, uint8_t day, uint8_t hour, uint8_t minute, uint64_t lengthOfCreator, string &creatorString, vector<Image> &images)
{
    this->year = year;
    this->month = month;
    this->day = day;
    this->hour = hour;
    this->minute = minute;
    this->lengthOfCreator = lengthOfCreator;
    this->creatorString = creatorString;
    this->images = images;
}
CreatorsImages::CreatorsImages()
{
    this->error = "Bad Format";
}

int main(int argc, char *argv[])
{
    if (argc == 2)
    {
        CreatorsImages ci = CaffReader::readCaffFromFile(argv[1], false);
        return 0;
    }
    else if (argc == 3)
    {
        CreatorsImages ci = CaffReader::readCaffFromFile(argv[1], true);
        return 0;
    }
    else
    {
        return -1;
    }
}

namespace CaffReader
{
    void generateJpegImages(vector<Image> images,
                            string creatorString,
                            uint16_t year,
                            uint8_t month,
                            uint8_t day,
                            uint8_t hour,
                            uint8_t minute)
    {
        uint64_t counter = 0;
        for (Image image : images)
        {
            std::vector<Pixel>::iterator it = image.pixels.begin();
            EasyBMP::RGBColor black(0, 0, 0);
            // sizeX, sizeY, FileName, BackgroundColor
            string filename = creatorString + "_" +
                              std::to_string(year) + "_" +
                              std::to_string(month) + "_" +
                              std::to_string(day) + "_" +
                              std::to_string(hour) + "_" +
                              std::to_string(minute) + "_" +
                              std::to_string(counter) + ".jpg";
            EasyBMP::Image img(image.width, image.height,
                               filename, black);
            counter++;

            for (int i = 0; i < image.height; i++)
            {
                for (int j = 0; j < image.width; j++)
                {
                    Pixel p = *it;
                    it++;
                    img.SetPixel(j, i, EasyBMP::RGBColor(p.red, p.green, p.blue));
                }
            }

            img.Write();
        }
    }

    CreatorsImages readCaffFromFile(const string &filename, const bool generateImages)
    {
        //Read from file
        ifstream fin(filename.c_str(), ios::in | ios::binary);
        if (!fin.is_open())
        {
            cerr << "error: open file for input failed!" << endl;
            return CreatorsImages();
        }

        fin.seekg(0, ios::end);
        int totalFileSize = fin.tellg();
        fin.seekg(0, ios::beg);

        uint8_t nextHeaderId = '\0';
        uint64_t nextHeaderLength = 0;
        uint64_t headerSizeCaff = 0;
        uint64_t headerSizeCiff = 0;
        uint64_t numberOfAnimatedCiffs = 0;
        uint16_t year = 0;
        uint8_t month = '\0';
        uint8_t day = '\0';
        uint8_t hour = '\0';
        uint8_t minute = '\0';
        uint64_t lengthOfCreator = 0;
        uint64_t duration = 0;
        uint64_t content_size = 0;
        uint64_t height = 0;
        uint64_t width = 0;
        char magicCaff[4];
        char magicCiff[4];
        string caption = "";
        string tags = "";
        string creatorString = "Unknown";
        vector<Image> images;

        while (!fin.eof())
        {
            fin.read((char *)&nextHeaderId, sizeof(uint8_t));
            fin.read((char *)&nextHeaderLength, sizeof(uint64_t));

            if (nextHeaderId == 1)
            {
                fin.read(magicCaff, sizeof(magicCaff));

                if (magicCaff[0] != 'C' && magicCaff[1] != 'A' && magicCaff[2] != 'F' && magicCaff[3] != 'F')
                {
                    return CreatorsImages();
                }

                fin.read((char *)&headerSizeCaff, sizeof(uint64_t));
                fin.read((char *)&numberOfAnimatedCiffs, sizeof(uint64_t));
            }
            else if (nextHeaderId == 2)
            {
                fin.read((char *)&year, sizeof(uint16_t));
                fin.read((char *)&month, sizeof(uint8_t));
                fin.read((char *)&day, sizeof(uint8_t));
                fin.read((char *)&hour, sizeof(uint8_t));
                fin.read((char *)&minute, sizeof(uint8_t));
                fin.read((char *)&lengthOfCreator, sizeof(uint64_t));
                if (lengthOfCreator != 0)
                {
                    char creator[lengthOfCreator];
                    fin.read(creator, sizeof(creator));
                    creatorString = creator;
                }
            }
            else if (nextHeaderId == 3)
            {
                tags = "";
                fin.read((char *)&duration, sizeof(uint64_t));

                uint64_t sizeBeforeCIFFReading = fin.tellg();

                //CIFF reading
                fin.read(magicCiff, sizeof(magicCiff));

                if (magicCiff[0] != 'C' && magicCiff[1] != 'I' && magicCiff[2] != 'F' && magicCiff[3] != 'F')
                {
                    return CreatorsImages();
                }

                fin.read((char *)&headerSizeCiff, sizeof(uint64_t));
                fin.read((char *)&content_size, sizeof(uint64_t));
                fin.read((char *)&width, sizeof(uint64_t));
                fin.read((char *)&height, sizeof(uint64_t));

                //Read string until \n newLine
                getline(fin, caption);

                //Tags read separated with \0 and then pixels
                uint64_t sizeOfTags = sizeBeforeCIFFReading + headerSizeCiff - fin.tellg();
                for (int i = 0; i < sizeOfTags; i++)
                {
                    char c;
                    fin.read((char *)&c, sizeof(char));
                    if (c == '\0')
                        c = ';';
                    tags += c;
                }

                //Read pixels
                vector<Pixel> pixels;
                for (int i = 0; i < width; i++)
                {
                    for (int j = 0; j < height; j++)
                    {
                        Pixel p;
                        fin.read((char *)&p, sizeof(Pixel));
                        pixels.push_back(p);
                    }
                }

                Image image(pixels, content_size, width, height, duration, tags, caption);
                images.push_back(image);
            }

            if (fin.tellg() == totalFileSize)
                break;
        }

        if (generateImages)
            generateJpegImages(images, creatorString, year, month, day, hour, minute);

        CreatorsImages creatorsImages(year, month, day, hour, minute, lengthOfCreator, creatorString, images);

        //Close file
        fin.close();

        return creatorsImages;
    }
} // namespace CaffReader
