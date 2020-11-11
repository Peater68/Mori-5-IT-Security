#include "com_mori5_itsecurity_cpp_CPPParserCaller.h"
#include <jni.h>

#include <iostream>
#include <fstream>
#include <list>
#include <cstring>
#include "vector"
#include "main.h"
#include "EasyBMP.hpp"

using namespace std;


JNIEXPORT jobject JNICALL Java_com_mori5_itsecurity_cpp_CPPParserCaller_parse
  (JNIEnv *env, jobject thisObject, jstring fileName){
 const char* nameCharPointer = env->GetStringUTFChars(fileName, NULL);
    std::string title;
    title = "";

    std::string filename = "1.caff";
    CreatorsImages image = CaffReader::readCaffFromFile(filename);

    jclass creatorsClass = env->FindClass("com/mori5/itsecurity/cpp/CreatorsImages");
    jobject newCreatorsImages = env->AllocObject(creatorsClass);

    jfieldID creatorName = env->GetFieldID(creatorsClass , "creatorString", "Ljava/lang/String;");

    uint64_t string_length;
    string_length = image.creatorString.length();
    char creator[string_length + 1];
    strcpy(creator, image.creatorString.c_str());
    jstring jstrCreator = env->NewStringUTF(creator);
    env->SetObjectField(newCreatorsImages, creatorName, jstrCreator);

    Image temp2 = image.images[0];

    jclass imageClass = env->FindClass("com/mori5/itsecurity/cpp/CaffImage");
    jobject newImage = env->AllocObject(imageClass);

    jfieldID content_size = env->GetFieldID(imageClass , "content_size", "J");
    jfieldID height = env->GetFieldID(imageClass , "height", "J");
    jfieldID width = env->GetFieldID(imageClass , "width", "J");

    env->SetLongField(newImage, content_size, (jlong) temp2.content_size);
    env->SetLongField(newImage, height, (jlong) temp2.height);
    env->SetLongField(newImage, width, (jlong) temp2.width);

    jfieldID imageJfield = env->GetFieldID(creatorsClass , "images", "Lcom/mori5/itsecurity/cpp/CaffImage;");

    jfieldID pixels = env->GetFieldID(imageClass , "pixels", "Ljava/util/ArrayList;");

    jclass clazz = env->FindClass("java/util/ArrayList");
    jobject obj = env->NewObject(clazz, env->GetMethodID(clazz, "<init>", "()V"));
    for (int n=0;n<temp2.pixels.size();n++)
    {
       Pixel temp;
       temp = temp2.pixels[n];

       jclass pixelClass = env->FindClass("Lcom/mori5/itsecurity/cpp/CaffPixel;");
       jobject newPixels = env->AllocObject(pixelClass);

       jfieldID red = env->GetFieldID(pixelClass , "red", "B");
       jfieldID green = env->GetFieldID(pixelClass , "green", "B");
       jfieldID blue = env->GetFieldID(pixelClass , "blue", "B");

       env->SetLongField(newPixels, red, (jbyte)temp.red);
       env->SetLongField(newPixels, green, (jbyte)temp.green);
       env->SetLongField(newPixels, blue, (jbyte)temp.blue);
       env->CallBooleanMethod(obj, env->GetMethodID(clazz, "add", "(Ljava/lang/Object;)Z"), newPixels);
    }

    env->SetObjectField(newImage, pixels, obj);
    env->SetObjectField(newCreatorsImages, imageJfield, newImage);

    return newCreatorsImages;
 }


Image::Image(vector<Pixel> &pixels, uint64_t content_size, uint64_t width, uint64_t height, uint64_t duration, string tags, string caption) {
    this->pixels = pixels;
    this->content_size = content_size;
    this->width = width;
    this->height = height;
    this->duration = duration;
    this->tags = tags;
    this->caption = caption;
}

CreatorsImages::CreatorsImages(uint16_t year, uint8_t month, uint8_t day, uint8_t hour, uint8_t minute, uint64_t lengthOfCreator, string creatorString, vector<Image> &images) {
    this->year = year;
    this->month = month;
    this->day = day;
    this->hour = hour;
    this->minute = minute;
    this->lengthOfCreator = lengthOfCreator;
    this->creatorString = creatorString;
    this->images = images;
}
CreatorsImages::CreatorsImages(){
    this->error = "Bad Format";
}

namespace CaffReader {
    CreatorsImages readCaffFromFile(const string &filename) {
        // TODO: read CAFF file block depending on the id (order of blocks is not fixed!)

        //Read from file
        ifstream fin(filename.c_str(), ios::in | ios::binary);
        if (!fin.is_open()) {
            cerr << "error: open file for input failed!" << endl;
            return CreatorsImages();
        }

        fin.seekg(0, ios::end);
        int totalFileSize = fin.tellg();
        fin.seekg(0, ios::beg);

        uint8_t nextHeaderId;
        uint64_t nextHeaderLength;
        uint64_t headerSizeCaff;
        uint64_t headerSizeCiff;
        uint64_t numberOfAnimatedCiffs;
        uint16_t year = 0;
        uint8_t month = '\0';
        uint8_t day = '\0';
        uint8_t hour = '\0';
        uint8_t minute = '\0';
        uint64_t lengthOfCreator = 0;
        uint64_t duration;
        uint64_t content_size;
        uint64_t height;
        uint64_t width;
        char magicCaff[4];
        char magicCiff[4];
        string caption;
        string tags;
        string creatorString = "Unknown";
        uint64_t counter = 0;
        vector<Image> images;


        while(!fin.eof()){
            fin.read((char *) &nextHeaderId, sizeof(uint8_t));
            fin.read((char *) &nextHeaderLength, sizeof(uint64_t));

            if(nextHeaderId == 1) {
                fin.read(magicCaff, sizeof(magicCaff));

                if (magicCaff[0] != 'C' && magicCaff[1] != 'A' && magicCaff[2] != 'F' && magicCaff[3] != 'F'){
                    return CreatorsImages();
                }

                fin.read((char *) &headerSizeCaff, sizeof(uint64_t));
                fin.read((char *) &numberOfAnimatedCiffs, sizeof(uint64_t));
            } else if(nextHeaderId == 2) {
                fin.read((char *) &year, sizeof(uint16_t));
                fin.read((char *) &month, sizeof(uint8_t));
                fin.read((char *) &day, sizeof(uint8_t));
                fin.read((char *) &hour, sizeof(uint8_t));
                fin.read((char *) &minute, sizeof(uint8_t));
                fin.read((char *) &lengthOfCreator, sizeof(uint64_t));
                if(lengthOfCreator != 0){
                    char creator[lengthOfCreator];
                    fin.read(creator, sizeof(creator));
                    creatorString = creator;
                }
            } else if(nextHeaderId == 3) {
                fin.read((char *) &duration, sizeof(uint64_t));

                uint64_t caffTotalSize = fin.tellg();

                //CIFF reading
                fin.read(magicCiff, sizeof(magicCiff));

                if (magicCiff[0] != 'C' && magicCiff[1] != 'I' && magicCiff[2] != 'F' && magicCiff[3] != 'F'){
                    return CreatorsImages();
                }

                fin.read((char *) &headerSizeCiff, sizeof(uint64_t));
                fin.read((char *) &content_size, sizeof(uint64_t));
                fin.read((char *) &width, sizeof(uint64_t));
                fin.read((char *) &height, sizeof(uint64_t));

                //Read string until \n newLine
                getline(fin, caption);

                //Tags read separated with \0 and then pixels
                uint64_t sizeOfTags = caffTotalSize + headerSizeCiff - fin.tellg();
                for (int i = 0; i < sizeOfTags; i++) {
                    char c;
                    fin.read((char *) &c, sizeof(char));
                    tags += c;
                }

                //Read pixels
                vector<Pixel> pixels;
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        Pixel p;
                        fin.read((char *) &p, sizeof(Pixel));
                        pixels.push_back(p);
                    }
                }

                Image image(pixels, content_size, width, height, duration, tags, caption);
                images.push_back(image);
            }

            if (fin.tellg() == totalFileSize) break;
        }

        //Create jpg files
        for (Image image : images) {
            std::vector<Pixel>::iterator it = image.pixels.begin();
            EasyBMP::RGBColor black(0, 0, 0);
            // sizeX, sizeY, FileName, BackgroundColor
            string filename = "E:\\adasdasd"+std::to_string(counter)+".jpg";
            EasyBMP::Image img(image.width, image.height,
                                filename, black);
            counter++;

            for (int i = 0; i < image.height; i++) {
                for (int j = 0; j < image.width; j++) {
                    Pixel p = *it;
                    it++;
                    img.SetPixel(j, i, EasyBMP::RGBColor(p.red, p.green, p.blue));
                }
            }

            img.Write();
        }

        CreatorsImages creatorsImages(year,month,day,hour,minute,lengthOfCreator,creatorString,images);

        //Close file
        fin.close();

        return creatorsImages;
    }
}